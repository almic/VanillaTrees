package net.palacemc;

import org.pepsoft.worldpainter.layers.Bo2Layer;
import org.pepsoft.worldpainter.layers.CustomLayer;
import org.pepsoft.worldpainter.layers.bo2.Bo2ObjectTube;
import org.pepsoft.worldpainter.layers.bo2.Structure;
import org.pepsoft.worldpainter.objects.WPObject;

import javax.vecmath.Point3i;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class Main {

    // Relative path of WorldPainter layers should be "VanillaTrees\worldpainter\ocean\parts" and this project should be in that path
    private static final Path layersPath = Paths.get("").resolve("../original/").toAbsolutePath().normalize();
    private static final Path outPath = Paths.get("").resolve("../").toAbsolutePath().normalize();

    private static final String[] layers = {
            //"cold_structures.layer",
            "cold_structures_mixed.layer",
            "cold_structures_static.layer",
            //"frozen_structures.layer",
            "frozen_structures_static.layer",
            //"lukewarm_structures.layer",
            "lukewarm_structures_mixed.layer",
            "lukewarm_structures_static.layer",
            //"ocean_structures.layer",
            "ocean_structures_static.layer"
    };

    public static void main(String[] args) {
        System.out.println("Loading layers");

        for (String layerName : layers) {
            System.out.println("Processing file: " + layerName);
            try {
                ObjectInputStream in = inputStreamOf(layersPath.resolve(layerName));

                CustomLayer loadedLayer = (CustomLayer) in.readObject();
                in.close();
                Bo2Layer layer = (Bo2Layer) loadedLayer;

                Bo2ObjectTube provider = (Bo2ObjectTube) layer.getObjectProvider();
                List<WPObject> objects = provider.getAllObjects();

                for (WPObject wpObject: objects) {
                    Structure object = (Structure) wpObject;
                    Point3i offset = object.guestimateOffset();

                    String name = object.getName();
                    if (name.startsWith("sideways")) {
                        offset.z = -2; // sink more into ground
                    } else if (name.startsWith("upsidedown")) {
                        offset.z = -3; // sink very far into ground
                    } else {
                        offset.z = -1; // everything else, only one block
                    }

                    object.setAttribute(WPObject.ATTRIBUTE_OFFSET, offset);
                }

                // Now we just save it back
                try {
                    ObjectOutputStream out = outputStreamOf(outPath.resolve(layerName));
                    out.writeObject(layer);
                    out.close();
                } catch (IOException ex) {
                    System.err.println("Unable to write layer to file: " + layerName);
                    ex.printStackTrace();
                }

            } catch (FileNotFoundException ex) {
                System.err.println("Unable to find the layer file " + layerName);
                ex.printStackTrace();
            } catch (IOException ex) {
                System.err.println("Unable to read layer file: " + layerName);
                ex.printStackTrace();
            } catch (ClassNotFoundException ex) {
                System.err.println("Could not parse layer object: " + layerName);
                ex.printStackTrace();
            }
        }

        System.out.println("Done!");
    }

    private static ObjectInputStream inputStreamOf(Path path) throws IOException {
        return new ObjectInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(path.toString()))));
    }

    private static ObjectOutputStream outputStreamOf(Path path) throws IOException {
        return new ObjectOutputStream(new GZIPOutputStream(new BufferedOutputStream(new FileOutputStream(path.toString()))));
    }

}
