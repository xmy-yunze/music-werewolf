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

    // 首页
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 等待大厅
    @GetMapping("/lobby/{roomId}")
    public String lobby(@PathVariable String roomId, Model model) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        model.addAttribute("roomId", roomId);
        model.addAttribute("hostName", room.getHostName());
        model.addAttribute("players", room.getPlayers());
        model.addAttribute("maxPlayers", room.getMaxPlayers());
        return "lobby";
    }

    // 游戏页面
    @GetMapping("/game/{roomId}/{playerName}")
    public String game(@PathVariable String roomId,
                       @PathVariable String playerName,
                       Model model) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        model.addAttribute("roomId", roomId);
        model.addAttribute("playerName", playerName);
        return "game";
    }

    // 投票页面
    @GetMapping("/vote/{roomId}/{playerName}")
    public String vote(@PathVariable String roomId,
                       @PathVariable String playerName,
                       Model model) {
        Room room = roomService.getRoom(roomId);
        if (room == null) {
            return "redirect:/";
        }
        model.addAttribute("roomId", roomId);
        model.addAttribute("playerName", playerName);
        model.addAttribute("players", room.getPlayers());
        return "vote";
    }
}