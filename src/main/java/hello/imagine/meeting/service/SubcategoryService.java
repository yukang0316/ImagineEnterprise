package hello.imagine.meeting.service;



import hello.imagine.meeting.model.MeetingCategory;
import hello.imagine.meeting.model.Subcategory;
import hello.imagine.meeting.repository.MeetingCategoryRepository;
import hello.imagine.meeting.repository.SubcategoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class SubcategoryService {
    private final SubcategoryRepository subcategoryRepository;
    private final MeetingCategoryRepository meetingCategoryRepository;

    @Autowired
    public SubcategoryService(SubcategoryRepository subcategoryRepository, MeetingCategoryRepository meetingCategoryRepository) {
        this.subcategoryRepository = subcategoryRepository;
        this.meetingCategoryRepository = meetingCategoryRepository;
    }


    // 하위 카테고리 리스트 뽑기
    public List<Subcategory> findAll() {
        return subcategoryRepository.findAll();
    }




    public List<Subcategory> findByParentCategoryId(Long categoryId) {
        return subcategoryRepository.findByParentCategoryId(categoryId);
    }


    public Subcategory save(Subcategory subcategory) {

        return subcategoryRepository.save(subcategory);
    }


    public Subcategory findById(Long id) {
        return subcategoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("하위 카테고리를 찾을 수 없습니다: ID = " + id));
    }


    // 고정된 하위 카테고리 데이터 초기화 메서드
    @PostConstruct
    public void init() {
        initializeSubcategories(1L, Arrays.asList("축구", "야구", "농구", "테니스", "클라이밍", "헬스", "기타"));
        initializeSubcategories(2L, Arrays.asList("서울", "경기도", "경북", "전북", "전남", "경남", "기타"));
        initializeSubcategories(3L, Arrays.asList("서울", "경기도", "경북", "전북", "전남", "경남", "기타"));
        initializeSubcategories(4L, Arrays.asList("한식", "양식", "중식", "일식", "디저트", "TV방영", "기타"));
        initializeSubcategories(5L, Arrays.asList("토익", "공무원", "코테", "한국사", "컴활", "토익스피킹", "기타"));
        initializeSubcategories(6L, Arrays.asList("영화", "드라마", "뮤지컬", "애니", "웹툰", "TV프로그램", "기타"));
    }

    private void initializeSubcategories(Long meetingCategoryId, List<String> subcategoryNames) {
        MeetingCategory parentCategory = meetingCategoryRepository.findById(meetingCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("상위 카테고리가 존재하지 않습니다. ID: " + meetingCategoryId));

        for (String name : subcategoryNames) {
            // 서브카테고리 존재 여부를 이름과 상위 카테고리 ID로 체크
            if (!subcategoryRepository.existsByNameAndParentCategoryId(name, meetingCategoryId)) {
                Subcategory subcategory = new Subcategory(name, parentCategory);
                subcategoryRepository.save(subcategory);
            }
        }
    }


}

