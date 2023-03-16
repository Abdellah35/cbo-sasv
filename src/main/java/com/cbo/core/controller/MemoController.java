package com.cbo.core.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import com.cbo.core.model.Memo;
import com.cbo.core.repo.MemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/api")
public class MemoController {

    Long refrefno;



    private final MemoRepository mrepository;

    @Autowired
    public MemoController(MemoRepository mrepository) {
        this.mrepository = mrepository;
    }


    //List all the memos
    @GetMapping("/memos")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<List<Memo>> getAllMemos(){


        try {
            List<Memo> memos = new ArrayList<Memo>();


            mrepository.findAll().forEach(memos::add);


            if(memos.isEmpty() ){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(memos,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/memos/{refno}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Memo> getMemoByRefno(@PathVariable("refno") long refno){

        Optional<Memo> memoData = mrepository.findByRefnom(refno);

        if(memoData.isPresent()){
            return new ResponseEntity<>(memoData.get(), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }


    }

    @PostMapping("/memos")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Memo> createMemo(@RequestBody Memo memo){
        try {
            Memo _memo = mrepository.save(memo);


            return new ResponseEntity<>(_memo, HttpStatus.CREATED);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @PutMapping("/memos/{refno}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<Memo> updateMemo(@PathVariable("refno") Long refno, @RequestBody Memo memo){
        Optional<Memo> memoData = mrepository.findByRefnom(refno);

        if (memoData.isPresent()) {
            Memo _memo = memoData.get();
            _memo.setCurdate(memo.getCurdate());
            _memo.setSendate(memo.getSendate());
            _memo.setToTo(memo.getToTo());
            _memo.setFromFrom(memo.getFromFrom());
            _memo.setCarbonCopy(memo.getCarbonCopy());
            _memo.setSubject(memo.getSubject());
            _memo.setBody(memo.getBody());

            return new ResponseEntity<>(mrepository.save(_memo),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/memos/{refno}")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<HttpStatus> deleteMemo(@PathVariable("refno") long refno){
        try {
            mrepository.deleteByRefnom(refno);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("/memos")
    @PreAuthorize("hasRole('DIRECTOR')")
    public ResponseEntity<HttpStatus> deleteTheHellOfIt(){
        try {
            mrepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}

