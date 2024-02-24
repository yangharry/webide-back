package king.ide.dto;

import king.ide.domain.Folders;
import king.ide.domain.Member;
import king.ide.domain.enums.Language;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class FolderDto {

    private Long id;
    private Long memberId; // 해당 폴더 소유자 ID
    private String foldername;
    private Long parentFolderId; // 상위 폴더 ID
    private LocalDateTime createdAt;
    private Language language;
    private List<PackageDto> packages; // 폴더에 속한 패키지 목록

    public Folders toEntity() {
        Folders folder = new Folders();
        folder.setId(this.id);

        // memberId
        Member member = new Member();
        member.setId(this.memberId);

        // folder.setMember(member);
        folder.setFoldername(this.foldername);
        folder.setParentFolderId(this.parentFolderId);
        folder.setCreatedAt(this.createdAt);
        folder.setLanguage(this.language);

        return folder;
    }
}
