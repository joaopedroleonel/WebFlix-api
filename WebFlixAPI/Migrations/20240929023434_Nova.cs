using Microsoft.EntityFrameworkCore.Migrations;

#nullable disable

namespace WebFlixAPI.Migrations
{
    /// <inheritdoc />
    public partial class Nova : Migration
    {
        /// <inheritdoc />
        protected override void Up(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GenreVideo_Genres_GenresGenreID",
                table: "GenreVideo");

            migrationBuilder.DropForeignKey(
                name: "FK_GenreVideo_Videos_VideosVideoID",
                table: "GenreVideo");

            migrationBuilder.RenameColumn(
                name: "Title",
                table: "Videos",
                newName: "title");

            migrationBuilder.RenameColumn(
                name: "ImageUrl",
                table: "Videos",
                newName: "imageUrl");

            migrationBuilder.RenameColumn(
                name: "Description",
                table: "Videos",
                newName: "description");

            migrationBuilder.RenameColumn(
                name: "VideoID",
                table: "Videos",
                newName: "videoID");

            migrationBuilder.RenameColumn(
                name: "VideosVideoID",
                table: "GenreVideo",
                newName: "VideosvideoID");

            migrationBuilder.RenameColumn(
                name: "GenresGenreID",
                table: "GenreVideo",
                newName: "GenresgenreID");

            migrationBuilder.RenameIndex(
                name: "IX_GenreVideo_VideosVideoID",
                table: "GenreVideo",
                newName: "IX_GenreVideo_VideosvideoID");

            migrationBuilder.RenameColumn(
                name: "Name",
                table: "Genres",
                newName: "name");

            migrationBuilder.RenameColumn(
                name: "GenreID",
                table: "Genres",
                newName: "genreID");

            migrationBuilder.AddForeignKey(
                name: "FK_GenreVideo_Genres_GenresgenreID",
                table: "GenreVideo",
                column: "GenresgenreID",
                principalTable: "Genres",
                principalColumn: "genreID",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_GenreVideo_Videos_VideosvideoID",
                table: "GenreVideo",
                column: "VideosvideoID",
                principalTable: "Videos",
                principalColumn: "videoID",
                onDelete: ReferentialAction.Cascade);
        }

        /// <inheritdoc />
        protected override void Down(MigrationBuilder migrationBuilder)
        {
            migrationBuilder.DropForeignKey(
                name: "FK_GenreVideo_Genres_GenresgenreID",
                table: "GenreVideo");

            migrationBuilder.DropForeignKey(
                name: "FK_GenreVideo_Videos_VideosvideoID",
                table: "GenreVideo");

            migrationBuilder.RenameColumn(
                name: "title",
                table: "Videos",
                newName: "Title");

            migrationBuilder.RenameColumn(
                name: "imageUrl",
                table: "Videos",
                newName: "ImageUrl");

            migrationBuilder.RenameColumn(
                name: "description",
                table: "Videos",
                newName: "Description");

            migrationBuilder.RenameColumn(
                name: "videoID",
                table: "Videos",
                newName: "VideoID");

            migrationBuilder.RenameColumn(
                name: "VideosvideoID",
                table: "GenreVideo",
                newName: "VideosVideoID");

            migrationBuilder.RenameColumn(
                name: "GenresgenreID",
                table: "GenreVideo",
                newName: "GenresGenreID");

            migrationBuilder.RenameIndex(
                name: "IX_GenreVideo_VideosvideoID",
                table: "GenreVideo",
                newName: "IX_GenreVideo_VideosVideoID");

            migrationBuilder.RenameColumn(
                name: "name",
                table: "Genres",
                newName: "Name");

            migrationBuilder.RenameColumn(
                name: "genreID",
                table: "Genres",
                newName: "GenreID");

            migrationBuilder.AddForeignKey(
                name: "FK_GenreVideo_Genres_GenresGenreID",
                table: "GenreVideo",
                column: "GenresGenreID",
                principalTable: "Genres",
                principalColumn: "GenreID",
                onDelete: ReferentialAction.Cascade);

            migrationBuilder.AddForeignKey(
                name: "FK_GenreVideo_Videos_VideosVideoID",
                table: "GenreVideo",
                column: "VideosVideoID",
                principalTable: "Videos",
                principalColumn: "VideoID",
                onDelete: ReferentialAction.Cascade);
        }
    }
}
