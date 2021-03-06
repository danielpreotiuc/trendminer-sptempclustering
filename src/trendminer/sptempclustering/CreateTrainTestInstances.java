/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package trendminer.sptempclustering;

import cc.mallet.types.InstanceList;
import cc.mallet.util.CommandOption;
import cc.mallet.util.Randoms;
import java.io.File;


/**
 *
 * @author andreavarga
 */
public class CreateTrainTestInstances {    

     static CommandOption.String instancesMalletFile =
            new CommandOption.String(CreateTrainTestInstances.class,
            "instancesMalletFile", "",
            true,
            "",
            "path to the mallet instances containing the data",
            null);

    static CommandOption.Double trainingPortion =
            new CommandOption.Double(ImportTrendMinerData.class,
            "trainingportion", "",
            false,
            0.7,
            "training proportion of the original mallet instance file in the range of 0.0 .. 1.0",
            null);


    public static void main(String[] args) {
        try {
            CommandOption.setSummary(CreateTrainTestInstances.class,
                    "Splitting the mallet instances into train and test files - developed between Dec 2013 - April 2014");
            CommandOption.process(CreateTrainTestInstances.class, args);
            CommandOption.printOptionValues(CreateTrainTestInstances.class);

            InstanceList insAll = InstanceList.load(new File(
                    instancesMalletFile.value
                    ));
            InstanceList[] insSRC_split = insAll.split(new Randoms(),
                            new double[]{trainingPortion.value,
                                1 - trainingPortion.value});
            
            String sInstancesTrainFileName = new File(
                    instancesMalletFile.value
                    ).getAbsoluteFile() +"train.mallet";
            
            String sInstancesTestFileName = new File(
                    instancesMalletFile.value
                    ).getAbsoluteFile() +"test.mallet";            
            
            System.out.println("Saving training files to "+sInstancesTrainFileName);
            insSRC_split[0].save(new File(
                    sInstancesTrainFileName
                    ));
            System.out.println("Saving test files to "+sInstancesTestFileName);
            insSRC_split[1].save(new File(
                    sInstancesTestFileName
                    ));            
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
