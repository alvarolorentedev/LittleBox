/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package andar.littlebox;

import andar.littlebox.graphics.Model3D;
import andar.littlebox.models.Model;
import andar.littlebox.parser.ObjParser;
import andar.littlebox.parser.ParseException;
import andar.littlebox.util.AssetsFileUtil;
import andar.littlebox.util.BaseFileUtil;

import android.os.Bundle;
import edu.dhbw.andar.ARToolkit;
import edu.dhbw.andar.AndARActivity;
import edu.dhbw.andar.exceptions.AndARException;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author knek
 */
public class ModelLoader extends AndARActivity
{

    ARToolkit artoolkit;
    private ArrayList<Model3D> model3d = new ArrayList();
    
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        super.setNonARRenderer(new CustomRenderer());
        artoolkit = getArtoolkit();
        Add3DModel("GlassRose.obj", "barcode.patt");
        Load3DModels();
        startPreview();
    }
    
    private void Add3DModel(String modelFileName, String pattern)
    {
       
        BaseFileUtil fileUtil = new AssetsFileUtil(getResources().getAssets());
        fileUtil.setBaseFolder("models/");
        if(modelFileName.endsWith(".obj")) 
        {
            ObjParser parser = new ObjParser(fileUtil);
            try 
            {          
                BufferedReader fileReader = fileUtil.getReaderFromName(modelFileName);
                if(fileReader != null) 
                {
                    Model model = parser.parse("Model", fileReader);
                    model3d.add(new Model3D(model,modelFileName, pattern));
                }

            } 
            catch (IOException e) 
            {
            } 
            catch (ParseException e) 
            {
            }
        }
    }
    
    public void uncaughtException(Thread thread, Throwable ex) {
    }

    private void Load3DModels() {
        try 
        {
            for(Model3D localModel : model3d)
            {
                artoolkit.registerARObject(localModel);
            }
        }
        catch (AndARException e) 
        {
                   
        }
        
    }
    
}
