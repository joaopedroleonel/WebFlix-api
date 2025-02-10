using Microsoft.EntityFrameworkCore;
using WebFlixAPI.Moldes;
namespace WebFlixAPI.Context;

public class ApiContext: DbContext
{
    public ApiContext(DbContextOptions<ApiContext> options) : base(options)
    {
        
    }

    public DbSet<Genre> Genres { get; set; }
    public DbSet<Video> Videos { get; set; }

}
