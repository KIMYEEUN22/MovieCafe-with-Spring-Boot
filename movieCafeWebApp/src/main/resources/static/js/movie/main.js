const movieList = [];

$(document).ready(function () {
	
	$.ajax({
		url: 'https://yts.mx/api/v2/list_movies.json?sort_by=like_count',
		method: 'GET',
		dataType: 'json',
		success: function (data) {
			$(".loadingBox").remove();
			const movies = data.data.movies;
			movies.forEach((movie) => movieList.push({
				id: movie.id,
				image: movie.medium_cover_image,
				title: movie.title
			}));
			
			$('#pagination-div').twbsPagination({
				totalPages: 7,	// 총 페이지 번호 수
				visiblePages: 3,	// 하단에서 한번에 보여지는 페이지 번호 수
				startPage: 1, // 시작시 표시되는 현재 페이지
				initiateStartPageClick: true,	// 플러그인이 시작시 페이지 버튼 클릭 여부 (default : true)
				first: "<<",	// 페이지네이션 버튼중 처음으로 돌아가는 버튼에 쓰여 있는 텍스트
				prev: "<",	// 이전 페이지 버튼에 쓰여있는 텍스트
				next: ">",	// 다음 페이지 버튼에 쓰여있는 텍스트
				last: ">>",	// 페이지네이션 버튼중 마지막으로 가는 버튼에 쓰여있는 텍스트
				nextClass: "page-item next",	// 이전 페이지 CSS class
				prevClass: "page-item prev",	// 다음 페이지 CSS class
				lastClass: "page-item last",	// 마지막 페이지 CSS calss
				firstClass: "page-item first",	// 첫 페이지 CSS class
				pageClass: "page-item",	// 페이지 버튼의 CSS class
				activeClass: "active",	// 클릭된 페이지 버튼의 CSS class
				disabledClass: "disabled",	// 클릭 안된 페이지 버튼의 CSS class
				anchorClass: "page-link",	//버튼 안의 앵커에 대한 CSS class

				onPageClick: function (event, page) {
					//클릭 이벤트
					let startRow = (page - 1) * 3;
					$(".movieList").empty();
					const movieList = data.data.movies;
					for (let i = startRow; i < startRow + 3; i++) {
						$(".movieList").append(`
							<div class="listSection">
							<div class="sectionMovie" id="movie">
								<img
									src="${movieList[i].medium_cover_image}"
									alt="movie" class="movieImg" onclick="location.href='detailMovie/${movieList[i].id}';"/>
								<h3 class="movie_title">${movieList[i].title}</h3>
							</div>
						</div>`);
					}
				}
			});
		},
		beforeSend: function () {
			$(".movieList").append(`
			<div class="loadingBox">
				<P class="loadingText">업데이트중</P>
				<div class="loader10"></div>
			</div> `);
		},
		error: function (error) {
			console.log(error);
		}
	});

});