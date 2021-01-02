package com.example.demo.upload;

import com.example.demo.uploadproduct.UploadProduct;
import com.example.demo.user.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Upload {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "upload_id")
    private Long id;

    private LocalDateTime uploadDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "upload", cascade = CascadeType.ALL)
    private List<UploadProduct> uploadProducts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private UploadStatus uploadStatus;

    // Upload - UploadProduct
    public void addUploadProduct(UploadProduct uploadProduct) {
        this.uploadProducts.add(uploadProduct);
        uploadProduct.setUpload(this);
    }

    // Upload - User
    public void addUser(User user) {
        this.user = user;
        user.getUploads().add(this);
    }

    public static Upload createUpload(User user, List<UploadProduct> uploadProducts) {
        Upload upload = new Upload();
        upload.setUser(user);
        upload.setUploadDateTime(LocalDateTime.now());
        upload.setUploadStatus(UploadStatus.UPLOAD);

        for (UploadProduct uploadProduct : uploadProducts)
            upload.addUploadProduct(uploadProduct);

        return upload;
    }

    public void cancel() {
        this.setUploadStatus(UploadStatus.CANCEL);
    }
}
