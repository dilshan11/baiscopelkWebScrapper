import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


class WebScarp {
    public static void main(String args[]){
       try {
           //get the movies list of a page
           getMoviePages();
       } catch (Exception e) {
           System.out.println(e);
       }
    }
    //full count 442
    public static void getMoviePages() throws Exception{
        for(int i=1;i<5;i++) {
            getMovieListOfPage(i);
        }
    }

    public static void getMovieListOfPage(int page) throws Exception{
        Document doc = Jsoup.connect("https://www.baiscope.lk/category/sinhala-subtitles/movies/page/"+page).timeout(10 * 10000).get();
        Elements divImageWrapper = doc.select("div.featured-image");
        for(Element element: divImageWrapper) {
            Elements li = element.select("a[href]"); // a with href
            String movieName = li.get(0).attr("title");
            String movieUrl = li.get(0).attr("href");
            getTheMovieDownloadCount(movieUrl,movieName);
        }
    }

    public static void getTheMovieDownloadCount(String movieUrl, String movieName) {
       try {

           Document doc = Jsoup.connect(movieUrl).timeout(10 * 10000).get();
           Elements authorDiv = doc.select("div.author-shortcodes");
           Element firstDiv = authorDiv.get(0);
           Element firstSibling = firstDiv.previousElementSibling();
           Element secondSibling = firstSibling.previousElementSibling();
           if (secondSibling.tag().getName().equals("p")) {
               secondSibling = secondSibling.previousElementSibling();
           }
           Element pElement = secondSibling.select("p").get(1);
           String downloadCount = pElement.select("span").text();
           System.out.println(movieName + " " + downloadCount);
       }catch (Exception e) {
           System.out.println("Exception is occured in getTheMovieDownloadCount");
       }
    }
}
