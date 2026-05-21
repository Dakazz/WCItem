package com.studwarcraft.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UploadedFile {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "uploaded_file_seq")
    @SequenceGenerator(name = "uploaded_file_seq", sequenceName = "uploaded_file_seq", allocationSize = 1)
    private Long fileid;

    private String filename;

    @Transient
    private File file;

    @ManyToMany(mappedBy = "uploadedFiles")
    @JsonIgnore  // umjesto @JsonBackReference
    private List<Item> items = new ArrayList<>();

    public UploadedFile() {}

    public Long getFileid() { return fileid; }
    public void setFileid(Long fileid) { this.fileid = fileid; }
    public String getFilename() { return filename; }
    public void setFilename(String filename) { this.filename = filename; }
    public File getFile() { return file; }
    public void setFile(File file) { this.file = file; }
    public List<Item> getItems() { return items; }
    public void setItems(List<Item> items) { this.items = items; }
}