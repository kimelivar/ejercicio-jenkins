package com.example.ServicioYouTube; 
 
import com.google.api.client.http.javanet.NetHttpTransport;  
import com.google.api.client.json.jackson2.JacksonFactory; 
  
import com.google.api.services.youtube.YouTube; 
import com.google.api.services.youtube.model.SearchListResponse; 
import com.google.api.services.youtube.model.SearchResult; 
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service; 
 
import java.io.IOException; 
import java.util.ArrayList; 
import java.util.List; 
 
@Service 
 
public class YouTubeService { 
 
    private final YouTube youtube; 
 
    @Value("${youtube.api.key}") 
    private String apiKey; 
 
    public YouTubeService() { 
        this.youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), 
                request -> {}).setApplicationName("music-manager").build(); 
    } 
 
    public List<String> searchVideos(String query) throws IOException { 
        YouTube.Search.List search = youtube.search().list("snippet"); 
        search.setQ(query); 
        search.setType("video"); 
        search.setKey(apiKey); 
        search.setMaxResults(5L); 
 
        SearchListResponse response = search.execute(); 
        List<String> videoTitles = new ArrayList<>(); 
 
        for (SearchResult result : response.getItems()) { 
            videoTitles.add(result.getSnippet().getTitle()); 
        } 
        return videoTitles; 
    } 
} 
