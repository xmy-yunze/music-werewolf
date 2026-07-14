package com.werewolf.musicwerewolf.controller;

import com.werewolf.musicwerewolf.model.Room;
import com.werewolf.musicwerewolf.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PageController {

    @Autowired
    private RoomService roomService;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/lobby/{roomId}")
    public String lobby(@PathVariable String roomId, Model model) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        model.addAttribute("roomId", roomId);
        model.addAttribute("hostName", room.getHostName());
        return "lobby";
    }

    @GetMapping("/game/{roomId}/{playerName}")
    public String game(@PathVariable String roomId,
                       @PathVariable String playerName,
                       Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("playerName", playerName);
        return "game";
    }

    @GetMapping("/vote/{roomId}/{playerName}")
    public String vote(@PathVariable String roomId,
                       @PathVariable String playerName,
                       Model model) {
        model.addAttribute("roomId", roomId);
        model.addAttribute("playerName", playerName);
        return "vote";
    }

    // ========== 新增：结果页面 ==========
    @GetMapping("/result/{roomId}")
    public String result(@PathVariable String roomId, Model model) {
        model.addAttribute("roomId", roomId);
        return "result";
    }
}