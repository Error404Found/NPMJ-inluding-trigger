package com.abhinendra.services.Implementation;



import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.abhinendra.domain.SanctionList;
import com.abhinendra.repositories.SanctionRepository;
import com.abhinendra.services.SanctionService;

@Service("sanctionService")
public class SanctionServiceImpl implements SanctionService{

	@Autowired
	SanctionRepository sanctionRepository;
	@Override
	public SanctionList saveSanctionEntry(SanctionList sanctionList) throws Exception {
		return sanctionRepository.save(sanctionList);
	}

	
	public ArrayList<String> arr=new ArrayList();
	
	@Override
	public ArrayList readSanctionList(String filename) {
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(filename));
			
			
			String line = new String();
			while((line=br.readLine()) != null)
			{
			    if(line.contains("Name"))
			    {
				    line = line.substring(9);
				    StringTokenizer st = new StringTokenizer(line,",");
				    SanctionList sanction;
				    while (st.hasMoreTokens()) {
				    		sanction = new SanctionList(st.nextToken().replaceAll("\\s", ""));
				    		arr.add(sanction.getName());
				    		try
				    		{
								saveSanctionEntry(sanction);
								
							} catch (Exception e) {
								e.printStackTrace();
							}
				    } 
			    }
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return arr;
	}
	
}
