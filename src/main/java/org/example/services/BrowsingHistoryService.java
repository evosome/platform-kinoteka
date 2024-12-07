package org.example.services;

import org.example.modules.BrowsingHistory;
import org.example.repositories.BrowsingHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BrowsingHistoryService {

    private final BrowsingHistoryRepository browsingHistoryRepository;

    @Autowired
    public BrowsingHistoryService(BrowsingHistoryRepository browsingHistoryRepository) {
        this.browsingHistoryRepository = browsingHistoryRepository;
    }

    public List<BrowsingHistory> getAllBrowsingHistory() {
        return browsingHistoryRepository.findAll();
    }

    public BrowsingHistory createBrowsingHistory(BrowsingHistory browsingHistory) {
        return browsingHistoryRepository.save(browsingHistory);
    }

    public BrowsingHistory getBrowsingHistoryById(long browsingHistoryId) {
        return browsingHistoryRepository.findById(browsingHistoryId)
                .orElseThrow(() -> new EntityNotFoundException("BrowsingHistory not found with id: " + browsingHistoryId));
    }

    public void deleteBrowsingHistory(long browsingHistoryId) {
        browsingHistoryRepository.deleteById(browsingHistoryId);
    }
}
