package king.ide.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import king.ide.domain.ChatRoom;
import king.ide.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@Validated
@RequestMapping("/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    //채팅 리스트 화면
    @GetMapping("/room")
    public String room() {
        log.info("채팅 리스트 메인화면");
        return "chat/room";
    }

    //채팅방 생성
    @PostMapping("/room")
    @ResponseBody
    public ChatRoom createRoom(@RequestParam @NotEmpty String name) {
        return chatRoomService.create(name);
    }

    //모든 채팅방 목록 json
    @GetMapping("/rooms")
    @ResponseBody
    public List<ChatRoom> rooms() {
        return chatRoomService.findAll();
    }

    //특정 채팅방 조회
    @GetMapping("/room/{roomId}")
    @ResponseBody
    public ChatRoom roomInfo(@PathVariable String roomId) {
        return chatRoomService.findById(roomId);
    }

    //채팅방 입장 화면
    @GetMapping("/room/enter/{roomId}")
    public String enterRoom(Model model, @PathVariable String roomId){
        model.addAttribute("roomId", roomId);
        return "chat/roomdetail";
    }

}
