package com.dnamobs.storage;

/**
 * Interface for data storage implementations
 */
public interface DataStorage {
    
    /**
     * Load data from storage
     */
    void loadData();
    
    /**
     * Save data to storage
     */
    void saveData();
    
    /**
     * Clear all data
     */
    void clearData();
}
