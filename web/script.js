document.addEventListener("DOMContentLoaded", function() {
  const form = document.getElementById('searchForm');
  const propertyResults = document.getElementById('propertyResults');
  const transactionResults = document.getElementById('transactionResults');
  const projectNameSelect = document.getElementById('projectName');

  // Function to populate project names in the dropdown
  function populateProjectNames() {
      // Example project names
      const projects = ["ProjectA", "ProjectB"];
      projects.forEach(project => {
          const option = document.createElement('option');
          option.value = project;
          option.textContent = project;
          projectNameSelect.appendChild(option);
      });
  }

  populateProjectNames();

  form.addEventListener('submit', function(event) {
      event.preventDefault();
      const size = document.getElementById('size').value;
      const minPrice = document.getElementById('minPrice').value;
      const maxPrice = document.getElementById('maxPrice').value;
      const facilities = document.getElementById('facilities').value;
      const projectName = document.getElementById('projectName').value;

      // Mock search results
      const properties = [
          {
              size: "1500 sq ft",
              price: 300000,
              facilities: "3 bedrooms, pool",
              projectName: "ProjectA",
              address: "123 Street"
          },
          {
              size: "1600 sq ft",
              price: 320000,
              facilities: "4 bedrooms, gym",
              projectName: "ProjectA",
              address: "456 Avenue"
          }
      ];

      // Clear previous results
      propertyResults.innerHTML = "";
      transactionResults.innerHTML = "";

      // Display search results
      properties.forEach(property => {
          const div = document.createElement('div');
          div.innerHTML = `
              <p><strong>Size:</strong> ${property.size}</p>
              <p><strong>Price:</strong> $${property.price}</p>
              <p><strong>Facilities:</strong> ${property.facilities}</p>
              <p><strong>Project Name:</strong> ${property.projectName}</p>
              <p><strong>Address:</strong> ${property.address}</p>
          `;
          propertyResults.appendChild(div);
      });

      // Mock historical transactions
      const transactions = [
          {
              projectName: "ProjectA",
              address: "123 Street",
              size: "1500 sq ft",
              price: 300000
          },
          {
              projectName: "ProjectA",
              address: "456 Avenue",
              size: "1600 sq ft",
              price: 320000
          }
      ];

      // Display historical transactions
      transactions.forEach(transaction => {
          const div = document.createElement('div');
          div.innerHTML = `
              <p><strong>Project Name:</strong> ${transaction.projectName}</p>
              <p><strong>Address:</strong> ${transaction.address}</p>
              <p><strong>Size:</strong> ${transaction.size}</p>
              <p><strong>Price:</strong> $${transaction.price}</p>
          `;
          transactionResults.appendChild(div);
      });
  });
});
