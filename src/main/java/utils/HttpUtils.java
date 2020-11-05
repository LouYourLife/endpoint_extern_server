package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.ChuckDTO;
import dtos.DadDTO;
import dtos.OurDTO;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

    //Callable
    class GetChuckDTO implements Callable<ChuckDTO> {
        String url;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        GetChuckDTO(String url) {
            this.url = url;
        }
        @Override
        public ChuckDTO call() throws Exception {
            String x = HttpUtils.fetchData("https://api.chucknorris.io/jokes/random");
            ChuckDTO chuckDTO = gson.fromJson(x, ChuckDTO.class);
            return chuckDTO;
    }
    }

    //Callable
    class GetDadDTO implements Callable<DadDTO> {
        String url;
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        GetDadDTO(String url) {
            this.url = url;
        }
        @Override
        public DadDTO call() throws Exception {
            String x = HttpUtils.fetchData("https://icanhazdadjoke.com");
            DadDTO dadDTO = gson.fromJson(x, DadDTO.class);
            dadDTO.setUrl("https://icanhazdadjoke.com");
            return dadDTO;
    }
    }

public class HttpUtils {

    public static String fetchData(String _url) throws MalformedURLException, IOException {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        //con.setRequestProperty("Accept", "application/json;charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty("User-Agent", "server");

        Scanner scan = new Scanner(con.getInputStream());
        String jsonStr = null;
        if (scan.hasNext()) {
            jsonStr = scan.nextLine();
        }
        scan.close();
        return jsonStr;
    }
    
    public static String jokeFetch() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String chuckURL = "https://api.chucknorris.io/jokes/random";
        String dadURL = "https://icanhazdadjoke.com";
        String chuck = HttpUtils.fetchData(chuckURL);
        String dad = HttpUtils.fetchData(dadURL);

        ChuckDTO chuckDTO = gson.fromJson(chuck, ChuckDTO.class);
        DadDTO dadDTO = gson.fromJson(dad, DadDTO.class);
        dadDTO.setUrl(dadURL);

        OurDTO combinedDTO = new OurDTO(chuckDTO, dadDTO);

        //This is what your endpoint should return       
        String combinedJSON = gson.toJson(combinedDTO);
        return combinedJSON;
    }
    
    public static String fetchDataParallel() throws InterruptedException, ExecutionException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        ExecutorService executor = Executors.newCachedThreadPool();
        String chuckURL = "https://api.chucknorris.io/jokes/random";
        String dadURL = "https://icanhazdadjoke.com";
        String[] urls = {chuckURL, dadURL};
        
        Callable<ChuckDTO> callC = new GetChuckDTO(chuckURL);
        Callable<DadDTO> callD = new GetDadDTO(dadURL);
        
        Future<ChuckDTO> futureC = executor.submit(callC);
        Future<DadDTO> futureD = executor.submit(callD);
        
        OurDTO our = new OurDTO(futureC.get(), futureD.get());
        String combined = gson.toJson(our);
        return combined;
    }
}
