package com.Toy2.product.picture.dto;

public class PictureUploadDto {
    private int fileId;
    private String fileName;
    private String fileData;

    public PictureUploadDto(String fileName, String file) {
        this.fileName = fileName;
        this.fileData = file;
    }

    public PictureUploadDto(int fileId, String fileName, String fileData) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileData = fileData;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileData() {
        return fileData;
    }

    public void setFileData(String fileData) {
        this.fileData = fileData;
    }

    @Override
    public String toString() {
        return "PictureUploadDto{" +
                "fileId=" + fileId +
                ", fileName='" + fileName + '\'' +
                ", fileData='" + fileData + '\'' +
                '}';
    }
}
