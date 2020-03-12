package com.anuj.devsocsignup.Adapters;

import java.io.Serializable;

public class FAQ implements Serializable {
    private String Q,A;

    public String getQ() {
        return Q;
    }

    public void setQ(String q) {
        Q = q;
    }

    public String getA() {
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
