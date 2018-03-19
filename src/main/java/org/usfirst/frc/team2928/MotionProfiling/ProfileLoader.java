package org.usfirst.frc.team2928.MotionProfiling;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProfileLoader {

    public Profile[] loadProfile(String profileName)
    {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        Profile[] profiles = new Profile[2];
        try
        {
            InputStream leftStream = loader.getResource("profile/" + profileName + "_left.csv").openStream();
            InputStream rightStream = loader.getResource("profile/" + profileName + "_right.csv").openStream();
            BufferedReader left = new BufferedReader(new InputStreamReader(leftStream));
            BufferedReader right = new BufferedReader(new InputStreamReader(rightStream));
            profiles[0] = fillProfile(left);
            profiles[1] = fillProfile(right);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return profiles;
    }

    private Profile fillProfile(BufferedReader br)
    {
        List<List<Double>> pointData = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null)
            {
                pointData.add(new ArrayList<>());
                String[] values = line.split(",");
                if (values[0] == null || values[0] == " ")
                    continue;
                for (int i = 0; i < 3; i++)
                    pointData.get(pointData.size() - 1).add(Double.parseDouble(values[i]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Profile(pointData);
    }
}