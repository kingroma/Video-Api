package com.mydefault.app.common.util;

public class GenericCode {

	// base URL 
	public static final int LIST 	= 10;				// 리스트 URL
	public static final int VIEW 	= 20;				// 상세조회 URL
	public static final int REGIST 	= 30;				// 등록 화면 
	public static final int EXCEL_JSP= 50;				// jsp 엑셀다운로드 화면 
	// redirect URL 
	public static final int LIST_REDIRECT    = 11;			// 리스트 리다이렉트
	public static final int LIST_ALL_REDIRECT= 12;			// 리스트 전체 리다이렉트
	public static final int VIEW_REDIRECT   = 21;			// 상세조회 리다이렉트 
	public static final int REGIST_REDIRECT = 31;			// 등록화면 리다이렉트 
	public static final int MODIFY_REDIRECT = 32;			// 수정화면 리다이렉트 
	
	public static final String TILES_SUB	= "tilessub";	// 서브 tiles 
	public static final String TILES_MAIN 	= "tilesmain";	// 메인 tiles 
	public static final String TILES_POPUP 	= "tilespopup";	// 팝업 tiles 
	public static final String TILES_PIECE 	= "tilespiece";	// 조각 tiles(header/footer없이 원본 그대로) 
	public static final String TILES_DASH 	= "tilesdash";	// 대시보드 tiles 
	
	// 처리 결과
	public static final String VALIDATE = "V";				// 밸리데이션 체크 실패
	public static final String SUCCESS  = "S";				// 성공 
	public static final String FAILURE  = "F";				// 실패  
	
	public static final String RESULT_CD = "resultCd";		// 결과코드
	public static final String MESSAGE 	 = "message";		// 메시지
	
	public static final String MSG_TY_F = "F";		// 실패한 메세지만 보고 싶을때
	
	public static final String MAIN_REDIRECT 	 = "/goMain.do";		// 메인페이지 URL
	public static final String LOGIN_REDIRECT 	 = "/login.do";			// 로그인 페잊 URL
	public static final String CLOSE_REDIRECT 	 = "/goClosePage.do";	// 닫기 페이지 URL
	
	public static final String EDIT_TY_I = "I";							// 입력 editer 타입 
	public static final String EDIT_TY_U = "U";							// 수정
	public static final String EDIT_TY_UD = "UD";						// 입력/수정
		
	public static final String ERROR_CD = "BIZ-ERROR : ";				// 에러 코드 명
	
	// 결재 메뉴ID
	public static final String LOGIN_TYPE_N = "NIN";		// 일반 로그인
	public static final String LOGIN_TYPE_S = "SSO";		// SSO로그인
	public static final String LOGIN_TYPE_C = "LUC";		// 사용자 변경
}
