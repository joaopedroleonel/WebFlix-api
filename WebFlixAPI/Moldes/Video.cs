using System.ComponentModel.DataAnnotations;

namespace WebFlixAPI.Moldes;

public class Video
{
    [Key]
    public int videoID { get; set; }

    [Required]
    [StringLength(80)]
    public string? title { get; set; }

    [StringLength(250)]
    public string? description { get; set; }

    [Required]
    [StringLength(250)]
    public string? imageUrl { get; set; }

    [Required]
    [StringLength(250)]
    public string? videoUrl { get; set; }
    public ICollection<Genre>? Genres { get; set;}

}
