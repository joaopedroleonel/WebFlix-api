using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using System.Linq;
using WebFlixAPI.Context;
using WebFlixAPI.DTOs;
using WebFlixAPI.Moldes;

namespace WebFlixAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class VideoController(ApiContext context) : ControllerBase
    {
        private readonly ApiContext _context = context;

        [HttpGet("videoList")]
        public ActionResult<IEnumerable<VideoListDTO>> GetVideosList()
        {

            var videos = _context.Videos.Include(g => g.Genres).Select(v => new VideoListDTO()
            {
                videoID = v.videoID,
                title = v.title,
                description = v.description,
                imageUrl = v.imageUrl,
                Genres = v.Genres
            }).ToList();

            if(videos is null || videos.Count == 0)
            {
                return NotFound();
            }

            return videos;

        }

        [HttpGet("{id:int}")]

        public ActionResult<IEnumerable<VideoPlayerDTO>> GetVideoPlayer(int id)
        {

            var videos = _context.Videos.Where(v => v.videoID == id).Select(v => new VideoPlayerDTO()
            {
                videoID = v.videoID,
                title = v.title,
                description = v.description,
                videoUrl = v.videoUrl,
                Genres = v.Genres
            }).ToList();

            if (videos is null || videos.Count == 0)
            {
                return NotFound();
            }

            return videos;

        }

        [HttpGet("genre")]
        public ActionResult<IEnumerable<VideoListDTO>> GetVideosListByGenre(int genreId)
        {

            var videos = _context.Videos.Include(g => g.Genres).Where(g => g.Genres.Any(ge => ge.genreID == genreId))
                .Select(v => new VideoListDTO()
                {
                    videoID = v.videoID,
                    title = v.title,
                    description = v.description,
                    imageUrl = v.imageUrl,
                    Genres = v.Genres
                }).ToList();

            if (videos is null || videos.Count == 0)
            {
                return NotFound();
            }

            return videos;

        }

    }
}
