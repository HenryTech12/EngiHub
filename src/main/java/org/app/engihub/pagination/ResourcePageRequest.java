package org.app.engihub.pagination;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ResourcePageRequest {
    private int pageNo;
    private int pageSize;
    private String sortBy = "course";

    public Pageable pageRequest(int pageNo, int pageSize , String sortBy) {
        int requestPageNo = Math.max(this.pageNo,pageNo);
        int requestPageSize = Math.max(this.pageSize,pageSize);
        String requestSort = (!Objects.isNull(sortBy)) ? sortBy : this.sortBy;

        return PageRequest.of(pageNo,pageSize, Sort.Direction.ASC,sortBy);
    }
}
