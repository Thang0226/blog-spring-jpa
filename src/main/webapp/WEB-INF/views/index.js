let searchResults;
let page;
let itemsPerPage = 2;

function searchTitle() {
    event.preventDefault();
    let words = $("#search-input").val();
    page = 0;
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/api/blogs/search/" + words,
        success: function (results) {
            searchResults = results;
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
    let lastIndex = results.length - 1;
    let startIndex = page * itemsPerPage;
    let endIndex = (lastIndex > ((page+1) * itemsPerPage - 1)) ? ((page+1) * itemsPerPage - 1) : lastIndex;
    for (let i = startIndex; i <= endIndex; i++) {
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

function moreSearchResults() {
    event.preventDefault();
    if (page < Math.floor(searchResults.length / itemsPerPage)) {
        page++;
    }
    showBlogs(searchResults);
}