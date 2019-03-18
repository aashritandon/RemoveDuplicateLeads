import com.marketo.Lead;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class LeadTest {

    @Test
    public void validateIsRecentThan(){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
            Lead newLead = new Lead();
            Date d1 = sdf.parse("03/18/2019");
            newLead.setEntryDate(d1);
            Lead oldLead = new Lead();
            Date d2 = sdf.parse("03/17/2019");
            oldLead.setEntryDate(d2);
            assertTrue(newLead.isRecentThan(oldLead));
        }
        catch (Exception e){
            System.out.println("Parse exception occured");
        }
    }
}
