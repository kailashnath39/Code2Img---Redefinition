package org.example;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.google.common.hash.Hashing;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

import static org.example.Main.*;
import static org.example.TypeMappings.*;
public class Func {

    public int funcId;

//    added for result analysis
    public int resFuncId;

//    for debugging purpose: showing what functions are there to be shown.
    public String funcSig;
    public String fileName;
    public int funcLen;
    public short[] vector;

    public int edgeNum;
    public List<Long> nLineHash;

    public HashMap<Long, String> hashContent = new HashMap<>();

    public Func(String fileName, CompilationUnit cu) throws IOException {
        this.fileName = fileName;
        setNLineHashAndVector(cu);
    }

    public static float nLineVerify(Func funcA, Func funcB, InvertedIndex invertedIndex) {
        var res = commonNLine(funcA, funcB, invertedIndex);

//        min function and max function will give same results mostly.
        int min_len = Math.min(funcA.funcLen, funcB.funcLen) - N + 1;
        return 1.0f * res / min_len;
//        int max_len = Math.max(funcA.funcLen, funcB.funcLen) - N + 1;
//        return 1.0f * res / max_len;
    }

    public static int commonNLine(Func funcA, Func funcB, InvertedIndex invertedIndex) {
        int res = 0;
        if (funcB.funcLen > funcA.funcLen) {
            Func tmp = funcB;
            funcB = funcA;
            funcA = tmp;
        }
        for (var hash : funcB.nLineHash) {
            if (invertedIndex.check(hash, funcA.funcId)) {
                res += 1;
            }
        }
        return res;
    }

    public static double normVecGenJacVerify(Func funcA, Func funcB) {
        var vec1 = funcA.vector;
        var sum1 = funcA.edgeNum;
        var vec2 = funcB.vector;
        var sum2 = funcB.edgeNum;
        int _a = 0, _b = 0, _c = 0;
        for (int i = 0; i < edgeTypeNum; i++) {
            _a += vec1[i] * vec2[i];
            _b += vec1[i] * vec1[i];
            _c += vec2[i] * vec2[i];
        }
        double a = 1.0 * _a / (sum1 * sum2);
        double b = 1.0 * _b / (sum1 * sum1);
        double c = 1.0 * _c / (sum2 * sum2);
        return a / (b + c - a);
    }

    private void setNLineHashAndVector(CompilationUnit cu) {
        short[] vector = new short[edgeTypeNum];
        List<String> normLines = new ArrayList<>();
        Queue<Node> queue = new LinkedList<>();
        queue.add(cu);
        int totalEdges = 0;
        while (!queue.isEmpty()) {
            var head = queue.poll();
            var str1 = head.getClass().getSimpleName();
            var children = head.getChildNodes();
            if (children.isEmpty()) {
                if (SimpleName.class.equals(head.getClass())) {
                    SimpleName sn = (SimpleName) head;
                    sn.setId("VAR1");
                } else if (Name.class.equals(head.getClass())) {
                    Name n = (Name) head;
                    n.setId("VAR2");
                } else if (StringLiteralExpr.class.equals(head.getClass())) {
                    StringLiteralExpr sle = (StringLiteralExpr) head;
                    sle.setString("STR");
                } else if (BooleanLiteralExpr.class.equals(head.getClass())) {
                    BooleanLiteralExpr ble = (BooleanLiteralExpr) head;
                    ble.setValue(false);
                } else if (IntegerLiteralExpr.class.equals(head.getClass())) {
                    IntegerLiteralExpr ile = (IntegerLiteralExpr) head;
                    ile.setValue("0");
                } else if (DoubleLiteralExpr.class.equals(head.getClass())) {
                    DoubleLiteralExpr dle = (DoubleLiteralExpr) head;
                    dle.setValue("0.0");
                }
                continue;
            }
            for (var child : children) {
                queue.offer(child);
                var str2 = child.getClass().getSimpleName();
                var index = edgeType2Num.get(str1 + str2) == null ? 0 : (int) edgeType2Num.get(str1 + str2);
                vector[index] += 1;
                totalEdges += 1;
            }
        }

        var lines = cu.toString().split("\\r?\\n");

        for (int i = 2; i < lines.length - 1; i++) {
            var line = lines[i].strip();
            if (!line.isEmpty()) {
                normLines.add(line);
            }
        }
        this.funcLen = normLines.size();
        setNLineHash(normLines);
        this.vector = vector;
        this.edgeNum = totalEdges;
    }


    public void setFuncId(int funcId) {
        this.funcId = funcId;
    }

    private void printNormalizedLines(List<String> normLines) {
        System.out.println("-----------------------------------------");
        for (String s: normLines) {
            System.out.println(s);
        }
        System.out.println("-----------------------------------------");
    }
    private void setNLineHash(List<String> normLines) {
//        printNormalizedLines(normLines);
        List<Long> nLineHash = new ArrayList<>();
        int len = normLines.size() - N + 1;

        for (int i = 0; i < len; i++) {
            StringBuilder tmp = new StringBuilder();
            for (int j = 0; j < N; j++) {
                tmp.append(normLines.get(i + j));
            }
            Long hashValue = Hashing.sipHash24().hashBytes(tmp.toString().getBytes()).asLong();
            hashContent.put(hashValue, tmp.toString());
            nLineHash.add(hashValue);
        }
        this.nLineHash = nLineHash;
    }

    public void copyVectorIntoFile() throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\kknat\\IdeaProjects\\code2imgFinal\\DetailedInspection\\Vectors\\" + resFuncId + ".txt");
        try {
            for (int i = 0; i < edgeTypeNum; i++) {
                if (vector[i] > 0) {
                    String buffer = edgeNum2Type.get(i + 1) + ": " + vector[i] + "\n";
                    fw.write(buffer);
                }
            }
            fw.close(); // Important to close FileWriter in the finally block (or try-with-resources in newer Java versions)
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e;
        }
    }

    public void copyNLineHashes() throws IOException {
        FileWriter fw = new FileWriter("C:\\Users\\kknat\\IdeaProjects\\code2imgFinal\\DetailedInspection\\NLineHashes\\" + resFuncId + ".txt");
        try {
//            System.out.println("helllo");
            for(Long hashValue: nLineHash) {
//                System.out.println(hashContent.get(hashValue) + "\n----------------------------------------\n");
                fw.write(hashValue.toString() + "\n");
//                fw.write(hashContent.get(hashValue) + "\n----------------------------------------\n");
            }
            fw.close(); // Important to close FileWriter in the finally block (or try-with-resources in newer Java versions)
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
            throw e;
        }
    }



}
