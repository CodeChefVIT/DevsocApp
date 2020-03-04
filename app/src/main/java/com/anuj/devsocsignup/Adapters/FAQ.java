package com.anuj.devsocsignup.Adapters;

public class FAQ {
    private String Q,A;

    String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    String getA() {
        return A;
    }

    public void setA(String a) {
        A = a;
    }

    public FAQ(String q, String a) {
        Q = q;
        A = a;
    }
}
