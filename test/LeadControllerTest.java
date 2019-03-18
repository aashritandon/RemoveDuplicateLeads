import com.marketo.Lead;
import com.marketo.LeadController;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class LeadControllerTest {

    private static Logger logger;

    @BeforeClass
    public static void beforeTest()throws IOException{
        FileHandler handler = new FileHandler("log.log", true);
        logger = Logger.getLogger("com.marketo");
        logger.addHandler(handler);
    }

    @Test
    public void readFileWithNoLeads()throws FileNotFoundException{
        LeadController lc = new LeadController();
        List<Lead> leadList =lc.readFile("./mockData/EmptyLead.json");
        assertTrue(leadList.isEmpty());
    }

    @Test
    public void readFileWithInvalidData()throws FileNotFoundException{
        LeadController lc = new LeadController();
        List<Lead> leadList =lc.readFile("./mockData/InvalidLeadJson.json");
        assertTrue(leadList.isEmpty());
    }

    @Test
    public void readFileValidData()throws FileNotFoundException{
        LeadController lc = new LeadController();
        List<Lead> leadList =lc.readFile("./mockData/Leads.json");
        assertEquals(leadList.size(), 6);
    }

    @Test
    public void filterListById()throws FileNotFoundException{
        LeadController lc = new LeadController();
        List<Lead> leadList =lc.readFile("./mockData/Leads.json");
        assertEquals(leadList.size(), 6);

        leadList = lc.filterList(leadList, true,logger);
        assertEquals(leadList.size(), 4);
    }

    @Test
    public void filterListByEmail()throws FileNotFoundException{
        LeadController lc = new LeadController();
        List<Lead> leadList =lc.readFile("./mockData/Leads.json");
        assertEquals(leadList.size(), 6);

        leadList = lc.filterList(leadList, false,logger);
        assertEquals(leadList.size(), 4);
    }

    @Test
    public void writeFileTestWithNull(){
        LeadController lc = new LeadController();
        lc.writeToFile(null,"");
        File f = new File("uniqueLeads.json");
        assertFalse(f.exists());
    }

    @Test
    public void writeFileTest(){
        LeadController lc = new LeadController();
        lc.writeToFile(new ArrayList<Lead>(),"");
        File f = new File("uniqueLeads.json");
        assertTrue(f.exists());
    }
}
