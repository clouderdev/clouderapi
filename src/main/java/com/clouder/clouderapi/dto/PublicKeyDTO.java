package com.clouder.clouderapi.dto;

public class PublicKeyDTO {

    private String e;
    private String n;
    private String maxdigits;

    public PublicKeyDTO() {
        // Default constructor
    }

    public PublicKeyDTO(String e, String n, String maxdigits) {
        super();
        this.e = e;
        this.n = n;
        this.maxdigits = maxdigits;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getN() {
        return n;
    }

    public void setN(String n) {
        this.n = n;
    }

    public String getMaxdigits() {
        return maxdigits;
    }

    public void setMaxdigits(String maxdigits) {
        this.maxdigits = maxdigits;
    }

    @Override
    public String toString() {
        return "PublicKeyDTO [e=" + e + ", n=" + n + ", maxdigits=" + maxdigits + "]";
    }

}
