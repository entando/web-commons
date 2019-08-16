package org.entando.test.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.entando.test.model.MockModel;
import org.entando.test.model.MockModelListProcessor;
import org.entando.web.exception.HttpException;
import org.entando.web.exception.NotFoundException;
import org.entando.web.request.PagedListRequest;
import org.entando.web.response.PagedMetadata;
import org.entando.web.response.PagedRestResponse;
import org.entando.web.response.SimpleRestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestMockController {

    private static final String JSON = MediaType.APPLICATION_JSON_VALUE;

    @GetMapping(path = "", produces = JSON)
    public PagedRestResponse<MockModel> list(PagedListRequest request)  {
        final List<MockModel> list = new MockModelListProcessor(request, Arrays.asList(
                new MockModel("John", "Doe", false, 31),
                new MockModel("Ryan", "Reynolds", true, 35)
        )).filterAndSort().toList();
        final PagedRestResponse<MockModel> response = new PagedRestResponse<>(new PagedMetadata<>(request, list.size()));
        response.setPayload(request.getSublist(list));

        return response;
    }

    @GetMapping(path = "/single", produces = JSON)
    public SimpleRestResponse<MockModel> single()  {
        return new SimpleRestResponse<>(new MockModel("John", "Doe", false, 31));
    }

    @GetMapping(path = "/badrequest", produces = JSON)
    public void badRequestEndpoint() {
        throw new HttpException(HttpStatus.BAD_REQUEST, "org.entando.test.badrequest");
    }

    @GetMapping(path = "/notfound", produces = JSON)
    public void notFoundEndpoint() {
        throw new NotFoundException("org.entando.test.notfound");
    }

}
