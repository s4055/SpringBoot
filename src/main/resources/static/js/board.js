let index={
		init:function(){
			$("#increase").on("click", ()=>{
				this.addLike();
			});
			$("#btn-comment-save").on("click", ()=>{
				this.commentSave();
			});
			$("#btn-comment-remove").on("click", ()=>{
				this.commentRemove();
			});
		},
		
		addLike:function(){
			var seq = $("#seq").val();

//			let data={
//					seq:$("#seq").val(),
//					lectureName:$("#lectureName").val(),
//			};
			// 자바스트립트 오브젝트
//			console.log(data);
			// JSON 문자열
//			console.log(JSON.stringify(data));
			
			// ajax호출시 default가 비동기 호출
			// ajax통신을 이용해서 데이터를 json으로 변경하여 요청
			$.ajax({
				type:"POST",
				url:"/AddLike",
				
				// http body데이터
				data:JSON.stringify(seq),
				
				// body데이터가 어떤 타입인지
				contentType:"application/json;charset=utf-8",
				
				// 요청을 서버로해서 응답이 왔을 때
				// 기본적으로 모든것이 문자열(생긴게 json이라면)=>javascript오브젝트로 변경
				dataType:"json"
			}).done(function(resp){
				alert("추천을 하시겠습니까?");
				
				var rs = JSON.stringify(resp)[22];
				if(rs == 1){
					alert("본인이 작성한 게시글은 추천할 수 없습니다.");
				}
				else if(rs == 2){
					alert("추천 성공했습니다.");
				}
				else if(rs == 3){
					alert("이미 추천하여 할 수 없습니다.");
				}
				
				location.href="/board/getBoard?seq=" + seq;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});		
		},
	
		commentSave:function(){
			var seq = $("#seq").val();
			var Ccontent = $("#comment-content").val();
			console.log(Ccontent);
			
			$.ajax({
				type:"POST",
				url:`/insertComment/${seq}`,
				data:JSON.stringify(Ccontent),
				contentType:"application/json;charset=utf-8",
				dataType:"json"
			}).done(function(resp){
				alert("댓글 등록 하시겠습니까?");
				location.href="/board/getBoard?seq=" + seq;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});	
		},
		
		commentRemove:function(){
			var seq = $("#seq").val();
			var Cseq = $("#Cseq").val();
			console.log(seq);
			console.log(Cseq);
			
			$.ajax({
				type:"POST",
				url:`/deleteComment/${seq}`,
				data:JSON.stringify(Cseq),
				contentType:"application/json;charset=utf-8",
				dataType:"json"
			}).done(function(resp){
				alert("댓글 삭제 하시겠습니까?");
				
				var rs = JSON.stringify(resp)[22];
				if(rs == 1){
					alert("댓글 삭제가 완료되었습니다");
				}
				else{
					alert("본인이 작성한 댓글 외에는 삭제하실 수 없습니다.");
				}
				location.href="/board/getBoard?seq=" + seq;
			}).fail(function(error){
				alert(JSON.stringify(error));
			});	
		}
}

index.init();