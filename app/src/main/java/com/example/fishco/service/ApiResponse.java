package com.example.fishco.service;

public class ApiResponse<T> {
    public T data; // Ganti sesuai dengan struktur JSON API
    public String message; // Optional, jika ada pesan dari API
    public boolean success; // Optional, jika API memberikan flag sukses

    // Tambahkan getter dan setter jika diperlukan
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
