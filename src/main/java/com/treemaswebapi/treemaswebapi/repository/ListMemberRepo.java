// package com.treemaswebapi.treemaswebapi.repository;

// import org.springframework.data.jpa.repository.JpaRepository;

// import com.treemaswebapi.treemaswebapi.entity.ListMemberProject;
// import java.util.List;


// public interface ListMemberRepo extends JpaRepository<ListMemberProject, String> {
//     ListMemberProject findByIdListMember(String idListMember);

//     List<String> findKodeProjectByNik(String nik);

//     default boolean isUserInListMemberProject(String idListMember) {
//         ListMemberProject listMemberProject = findByIdListMember(idListMember);
//         return listMemberProject != null;
//     }
// }
