package org.example;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class InvertedIndex {
    private final HashMap<Long, HashSet<Integer>> m = new HashMap<>();

    synchronized public void update(Func func) {
        for (var hash : func.nLineHash) {
            if (m.containsKey(hash)) {
                m.get(hash).add(func.funcId);
            } else {
                HashSet<Integer> hs = new HashSet<>();
                hs.add(func.funcId);
                m.put(hash, hs);
            }
        }
    }

    public boolean check(Long hash, Integer funcId) {
        return m.containsKey(hash) && this.get(hash).contains(funcId);
    }

    public HashSet<Integer> get(Long hash) {
        return m.get(hash);
    }

    public int size() {
        return m.size();
    }

    public void printIndexIntoFile() throws IOException {
        try(FileWriter fw = new FileWriter("C:\\Users\\kknat\\IdeaProjects\\code2imgFinal\\DetailedInspection\\InvertedIndex.txt", true)) {
            m.forEach((id, hashValues) -> {
                try {
                    fw.write("Func Id: " + id + " -> " + Arrays.toString(hashValues.toArray()) + "\n");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        catch (IOException io) {
            System.out.println(io);
            System.exit(0);
        }

    }
}