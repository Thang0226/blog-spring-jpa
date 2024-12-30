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
        <td><a th:href="@{/blogs/{id}/view(id=${results[i].id})}">${results[i].title}</a></td>
        <td>${results[i].author}</td>
        <td>
            <img width="150" height="100" th:src="@{'/images/' + ${results[i].imageFile}}" alt="Blog Image">
        </td>
        <td>
            <a th:href="@{/blogs/{id}/update(id=${results[i].id})}">Update</a>
            <a th:href="@{/blogs/{id}/delete(id=${results[i].id})}">Delete</a>
        </td>
    </tr>`
    }
    display += `</tbody></table>`;
    table.html(display);
}