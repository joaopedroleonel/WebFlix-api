using System.ComponentModel.DataAnnotations;
using System.Text.Json.Serialization;

namespace WebFlixAPI.Moldes;

public class Genre
{
    [Key]
    public int genreID { get; set; }

    [Required]
    [StringLength(50)]
    public string? name { get; set;}

    [JsonIgnore]
    public ICollection<Video>? Videos { get; set; }

}
