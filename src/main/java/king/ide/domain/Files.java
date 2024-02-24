package king.ide.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id")
    private Packages packages;

    @ManyToOne
    @JoinColumn(name = "folder_id")
    private Folders folders;

    @OneToOne(mappedBy = "files")
    private Codes codes;

    private String filename;
    private String filepath;
    private Long fileSize;
    private String fileType;
}
