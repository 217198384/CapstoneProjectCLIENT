package za.ac.cput.views;

import org.json.JSONArray;
import org.json.JSONObject;
import za.ac.cput.entity.person.Student;
import com.google.gson.Gson;
import okhttp3.*;

import java.io.IOException;

public class App {
    private static OkHttpClient client = new OkHttpClient();

    private static String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }

    public static void getAll() {
        try {
            final String URL = "http://localhost:8080/student/getall";
            String responseBody = run(URL);
            JSONArray students = new JSONArray(responseBody);

            for (int i = 0; i < students.length(); i++) {
                JSONObject student = students.getJSONObject(i);

                Gson g = new Gson();
                Student s = g.fromJson(student.toString(), Student.class);
                System.out.println(s.toString());
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        getAll();
    }
}
