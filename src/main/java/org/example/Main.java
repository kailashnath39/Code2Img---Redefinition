package org.example;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;



public class Main {
    public static String txlPath = "";
    public static String dirPath = "";

    public static String outputPath = "";
    public static int N = 3;

    public static int MINIMAL_FUNC_LINE_NUM = 6;

    public static float filter_score = 0.1f;
    public static float verify_score = 0.7f;
//    public static float final_verify_score = 0.015f;
    public static double vector_dis_verify_score = 0.9;

    public static int threadNum = 8;

    public static synchronized void addFunc(Func func, List<Func> data) {
        func.setFuncId(data.size());
        data.add(func);
    }

    public static void main(String[] args) {


//        N = Integer.parseInt(args[0]);
//        filter_score = Float.parseFloat(args[1]);
//        verify_score = Float.parseFloat(args[2]);
//        vector_dis_verify_score = Double.parseDouble(args[3]);
//        threadNum = Integer.parseInt(args[4]);
//        dirPath = args[5];
//        outputPath = args[6];
//        txlPath = args[7];


        System.out.println("N: " + N);
        System.out.println("filter_score: " + filter_score);
        System.out.println("verify_score: " + verify_score);
        System.out.println("vector_dis_verify_score: " + vector_dis_verify_score);
        System.out.println("threadNum: " + threadNum);
        System.out.println("dirPath: " + dirPath);
        System.out.println("outputPath: " + outputPath);
        System.out.println("txlPath: " + txlPath);


        long startTime = System.currentTimeMillis();
        File dir = new File(dirPath);
        var fileList = getAllJavaFiles(dir);
        if (fileList == null) {
            System.out.println("Not found any input file");
            System.exit(-1);
        }
        else {
            System.out.println("found some input files");
            for(var i: fileList) {
                System.out.println(i);
            }
        }
        System.out.println("************************************************");

        TaskList<String> parseTaskList = new TaskList<>(fileList);

        ArrayList<Thread> parseThreadList = new ArrayList<>();
        List<Func> data = new ArrayList<>();
        for (int i = 0; i < threadNum; i++) {
            var thread = new Thread(() -> {
                String fn = parseTaskList.getTask();
                while (fn != null) {
                    parseFile(fn, data);
                    fn = parseTaskList.getTask();
                }
            });
            thread.start();
            parseThreadList.add(thread);
        }


        for (var thread : parseThreadList) {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("Number of files: " + fileList.size());
        System.out.println("Number of functions: " + data.size());
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;
        System.out.println("Parse time: " + totalTime / 1000f);

        startTime = System.currentTimeMillis();
        AtomicLong totalClonePairsNum = new AtomicLong();
        TaskList<Func> detectTaskList = new TaskList<>(data);
        InvertedIndex invertedIndex = new InvertedIndex();
        detectTaskList.getItems().sort(Comparator.comparingInt(o -> o.funcLen));
        for (int j = 0; j < detectTaskList.size(); j++) {
            Func t = detectTaskList.getItem(j);
            t.setFuncId(j);
            invertedIndex.update(detectTaskList.getItem(j));

            System.out.println(t.funcId + "  " + t.funcSig);


        }


        ArrayList<Thread> detectThreadList = new ArrayList<>();
        for (int j = 0; j < threadNum; j += 1) {
            int threadId = j;
            var thread = new Thread(() -> {
                var funcC = detectTaskList.getTask();
                File writeFile = new File(outputPath + File.separator + "output" + threadId + ".csv");
                while (funcC != null) {
                    HashSet<Integer> cloneCandidate = new HashSet<>();
                    List<Integer> res = new ArrayList<>();
                    for (var nLineHash : funcC.nLineHash) {
                        var candidates = invertedIndex.get(nLineHash);
                        if (candidates != null) {
                            for (var c : candidates) {
                                if (c > funcC.funcId) {
                                    cloneCandidate.add(c);
                                }
                            }
                        }
                    }
                    for (var candidate : cloneCandidate) {
                        var funcB = detectTaskList.getItem(candidate);
                        var nLineVerifyScore = Func.nLineVerify(funcB, funcC, invertedIndex);
                        if (nLineVerifyScore >= verify_score) {
                            res.add(funcB.funcId);
                        } else if (nLineVerifyScore >= filter_score) {
                            var vectorVerifyScore = Func.normVecGenJacVerify(funcB, funcC);
                            if (vectorVerifyScore >= vector_dis_verify_score) {
                                res.add(funcB.funcId);
                            }
                        }
                    }
                    if (!res.isEmpty()) {
                        totalClonePairsNum.addAndGet(res.size());
                        try (BufferedWriter bw = new BufferedWriter(new FileWriter(writeFile, true))) {
                            for (var id : res) {
                                bw.write(funcC.funcId + "," + id);
                                bw.newLine();
                                bw.write(funcC.funcSig);
                                bw.newLine();
                                bw.write(detectTaskList.getItem(id).funcSig);
                                bw.newLine();
                                bw.write("-------------------------------------------------");
                                bw.newLine();
                            }
                            bw.flush();
                        } catch (Exception e) {
                            System.out.println("Can not write into " + writeFile);
                        }
                    }
                    funcC = detectTaskList.getTask();
                }

            });
            thread.start();
            detectThreadList.add(thread);
        }
        for (var thread : detectThreadList) {
            try {
                thread.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Detection time: " + totalTime / 1000f);
        System.out.println("Clone pairs num: " + totalClonePairsNum);

    }

    static List<String> getAllJavaFiles(File dir) {
        if (!dir.isDirectory()) {
            System.out.println("Invalid path: " + dirPath);
            return null;
        }
        System.out.println("Valid path");
        List<String> ret = new ArrayList<>();
        var files = dir.listFiles();
        if (files != null) {
            for (var f : files) {
                if (f.toString().endsWith(".java")) {
                    ret.add(f.toString());
                } else if (f.isDirectory()) {
                    var fs = getAllJavaFiles(f);
                    if (fs != null && !fs.isEmpty()) {
                        ret.addAll(fs);
                    }
                }
            }
        }
        return ret;
    }


    static int parseFile(String filePath, List<Func> data) {
        // for debugging purpose
        String funName = "";

        String cmd = "txl -q " + filePath + " " + txlPath;
        int ret = 0;
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(Runtime.getRuntime().exec(cmd).getInputStream(), StandardCharsets.UTF_8));
            String line;
            String fn = "";
            int sl = 0;
            int el = 0;
            StringBuilder sb = new StringBuilder();
            int min_length = Math.max(MINIMAL_FUNC_LINE_NUM, N);

            while ((line = br.readLine()) != null) {
                if (line.startsWith("<source file=")) {
                    var ss = line.split(" ");
                    fn = ss[1].substring(6, ss[1].length() - 1);
                    sl = Integer.parseInt(ss[2].substring(11, ss[2].length() - 1));
                    el = Integer.parseInt(ss[3].substring(9, ss[3].length() - 2));
                    sb = new StringBuilder();
                } else if (line.startsWith("</source>")) {
                    int funcLen = el - sl + 1;
                    if (funcLen >= min_length) {
                        try {
                            String code = "class _a {" + sb + "}";
                            CompilationUnit cu = StaticJavaParser.parse(code);

                            Func func = new Func(fn, cu);

                            if (func.funcLen >= min_length) {
                                func.funcSig = funName;
                                addFunc(func, data);
                                ret++;
                            }
                        } catch (Exception e) {
                            return 0;
                        }
                    }
                } else {
                    if(line.startsWith("public")) {
                        funName = line;
                    }
                    sb.append(line).append("\n");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
            return 0;
        }
        return ret;
    }

}