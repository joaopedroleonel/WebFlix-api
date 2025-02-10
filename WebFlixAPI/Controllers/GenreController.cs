using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using WebFlixAPI.Context;
using WebFlixAPI.Moldes;

namespace WebFlixAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class GenreController(ApiContext context) : ControllerBase
    {

        private readonly ApiContext _context = context;

        [HttpGet]

        public  ActionResult<IEnumerable<Genre>> Get()
        {

            var genres = _context.Genres.ToList();

            if (genres is null || genres.Count == 0)
            {
                return NotFound();
            }

            return genres;

        }
    }
}
