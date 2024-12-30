function searchTitle() {
    event.preventDefault();
    let words = $("#search-input").val();
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/blogs/search/" + words,
        success: function (results) {
            showBlogs(results);
        }
    })
}

function showBlogs(results) {
    let table = $("#list");
    let display = `<thead>
    <tr>
        <th>Time</th>
        <th>Title</th>
        <th>Author</th>
        <th>Image</th>
        <th>Actions</th>
    </tr>
    </thead>
    <tbody>`;
    for (let i = 0; i < results.length; i++) {
        display += `<tr>
        <td>${results[i].time}</td>
        <td><a href="/blogs/${results[i].id}/view">${results[i].title}</a></td>
        <td>${results[i].author}</td>
        <td>
            <img width="150" height="100" src="/images/${results[i].imageFile}" alt="Blog Image">
        </td>
        <td>
            <a href="/blogs/${results[i].id}/update">Update</a>
            <a href="/blogs/${results[i].id}/delete">Delete</a>
        </td>
    </tr>`
    }
    display += `</tbody>`;
    table.html(display);
}