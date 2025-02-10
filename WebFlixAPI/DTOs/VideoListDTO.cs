using System.ComponentModel.DataAnnotations;
using WebFlixAPI.Moldes;

namespace WebFlixAPI.DTOs;

public class VideoListDTO
{
    public int videoID { get; set; }

    [Required]
    [StringLength(80)]
    public string? title { get; set; }

    [StringLength(250)]
    public string? description { get; set; }

    [Required]
    [StringLength(250)]
    public string? imageUrl { get; set; }
    public ICollection<Genre>? Genres { get; set; }

}
