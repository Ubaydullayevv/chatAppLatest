package com.example.our_chat_app.repository;

import com.example.our_chat_app.entity.Group;
import com.example.our_chat_app.projection.ChannelProjection;
import com.example.our_chat_app.projection.PostProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChannelRepository extends JpaRepository<Group, Long> {

    @Query(nativeQuery = true,
    value = "select cast(g.id as varchar) as channelId,\n" +
            "       g.name as channelName,\n" +
            "       gm.text as channelLastPost,\n" +
            "       gm.created_at as lastPostTime\n" +
            "from groups g\n" +
            "         join group_message gm on g.id = gm.group_id\n" +
            "where g.is_channel=true\n" +
            "    order by channelLastPost desc limit 1")
    List<ChannelProjection> getAllChannels();


    @Query(nativeQuery = true,
    value = "select cast(gm.id as varchar) as postId,\n" +
            "       gm.text as postText,\n" +
            "       gm.created_at as postTime,\n" +
            "       gm.is_edit,\n" +
            "       gm.view_count\n" +
            "from group_message gm")

    List<PostProjection> getAllPosts();





}
