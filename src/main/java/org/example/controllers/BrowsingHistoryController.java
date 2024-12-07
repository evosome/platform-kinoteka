package org.example.controllers;

import org.example.modules.BrowsingHistory;
import org.example.services.BrowsingHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api1/v1")
public class BrowsingHistoryController {

    public static BrowsingHistoryService browsingHistoryService;

    @Autowired
    public BrowsingHistoryController(BrowsingHistoryService browsingHistoryService) {
        BrowsingHistoryController.browsingHistoryService = browsingHistoryService;
    }

    @GetMapping("/browsing-history")
    public List<BrowsingHistory> getAllBrowsingHistory() {
        return browsingHistoryService.getAllBrowsingHistory();
    }

    @PostMapping("/browsing-history")
    public ResponseEntity<BrowsingHistory> createBrowsingHistory(@RequestBody BrowsingHistory browsingHistory) {
        BrowsingHistory createdBrowsingHistory = browsingHistoryService.createBrowsingHistory(browsingHistory);
        return new ResponseEntity<>(createdBrowsingHistory, HttpStatus.CREATED);
    }

    @GetMapping("/browsing-history/{id}")
    public ResponseEntity<BrowsingHistory> getBrowsingHistoryById(@PathVariable Long id) {
        BrowsingHistory browsingHistory = browsingHistoryService.getBrowsingHistoryById(id);
        return new ResponseEntity<>(browsingHistory, HttpStatus.OK);
    }

    @DeleteMapping("/browsing-history/{id}")
    public ResponseEntity<Void> deleteBrowsingHistory(@PathVariable Long id) {
        browsingHistoryService.deleteBrowsingHistory(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
