package com.abhinendra.services;

import java.util.ArrayList;

import com.abhinendra.domain.*;

public interface SanctionService {
    public SanctionList saveSanctionEntry(SanctionList sanctionList) throws Exception;
    public ArrayList readSanctionList(String filename);
}