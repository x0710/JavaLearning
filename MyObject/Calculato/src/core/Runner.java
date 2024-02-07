package core;

import java.math.BigDecimal;
import java.util.*;
import java.io.*;
import java.net.*;

public class Runner {
    private BigDecimal value;
    private HashMap<String, Algorithm> modes;

    public Runner() {
        Iterator<Algorithm> i = getAlgorithms().iterator();
        value = new BigDecimal(0);
        modes = new HashMap<String, Algorithm>();
        while(i.hasNext()) {
            Algorithm t = i.next();
            System.out.println(t.getIdentifier());
            modes.put(t.getIdentifier(), t);
        }
    }

    /**
     *
     * @param identifier Algorithms imported.
     * @return Whether the operation is successful.
     */
    public boolean manipulate(String identifier, BigDecimal operationValue) {
        Algorithm a = modes.get(identifier);
        if(a == null) {
            System.out.println(false);
            return false;
        }
        value = a.run(value, operationValue);
        return true;
    }

    private ArrayList<Algorithm> getAlgorithms() {
        ArrayList<Algorithm> result = new ArrayList<Algorithm>();
        try {
            URI u = Thread.currentThread().getContextClassLoader().getResource("algorithm").toURI();
            File file = new File(u);
            for(File f : file.listFiles()) {
                String fStr = "algorithm." + f.getName().replaceAll("\\.\\w*", "");
                Object o = null;
                try {
                    o = Thread.currentThread().getContextClassLoader().loadClass(fStr).newInstance();
                }
                catch (Exception e) {
                    continue;
                }
                if(o instanceof Algorithm) {
                    result.add((Algorithm)o);
                }
            }
        }
        catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return result;
    }
    public String toString() {
        return value.toString();
    }
}
