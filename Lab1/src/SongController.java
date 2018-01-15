import Models.Song;

import java.util.Date;
import java.util.Locale;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet( name="Song Controller",urlPatterns={"/"} )
public class SongController extends HttpServlet {

    private final DateFormat format = new SimpleDateFormat( "yyyy-MM-dd", Locale.ENGLISH );

    private ArrayList<Song> songs;

    public SongController()
    {
        songs = new ArrayList<>();
    }


    @Override
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        PrintWriter out = response.getWriter();
        out.print( createHtml() );
        out.flush();
    }

    @Override
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        addSong(request);

        PrintWriter out = response.getWriter();
        out.print( createHtml() );
        out.flush();
    }

    private void addSong(HttpServletRequest request) {
        Song song = new Song();
        song.name = request.getParameter( "song" );
        song.author = request.getParameter( "author" );
        song.place = Integer.parseInt( request.getParameter( "place" ) );

        String dateStr = request.getParameter( "date" );
        try {
            song.date = format.parse( dateStr );
        } catch ( ParseException ignored ) {
            song.date = new Date();
        }

        songs.add(song);
    }

    private String createHtml() {
        String html =
                "<html>" +
                "<head>" +
                "<title>" +
                "Songs" +
                "</title>" +
                "</head>" +
                "<body>" +
                "<form action=\"SongController\" method=\"POST\">" +
                "name: <input type=\"text\" name=\"name\">" +
                "<br>" +
                "author: <input type=\"text\" name=\"author\" />" +
                "<br>" +
                "place: <input type=\"number\" name = \"place\" /> $" +
                "<br>" +
                "date: <input type=\"date\" name=\"date\" />" +
                "<br>" +
                "<input type=\"submit\" value=\"Add song\" />" +
                "</form>";

        html += createSongsHtml();

        html += "</table>" +
                "</body>" +
                "</html>";

        return html;
    }

    private String createSongsHtml() {
        String songsHtml =
                "<p>Added songs</p>" +
                "<table>" +
                "<tr>" +
                "<th>name</th>" +
                "<th>author</th>" +
                "<th>place</th>" +
                "<th>date</th>" +
                "</tr>";

        for ( Song song : songs) {
            songsHtml +=
                    "<tr>" +
                    "<td>" + song.name + "</td>" +
                    "<td>" + song.author + "</td>" +
                    "<td>" + song.place + "$</td>" +
                    "<td>" + new SimpleDateFormat("MM-dd-yyyy").format(song.date) + "</td>" +
                    "</tr>";
        }

        return songsHtml;
    }
}
