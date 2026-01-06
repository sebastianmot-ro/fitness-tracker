const userId = 1; // ID-ul user-ului pe care vrem sa-l folosim
const baseUrl = `/users/${userId}/activities`;

const activityForm = document.getElementById('activityForm');
const activitiesList = document.getElementById('activitiesList');

// Fetch existing activities
function fetchActivities() {
    fetch(baseUrl)
        .then(res => res.json())
        .then(data => {
            activitiesList.innerHTML = '';
            data.forEach(act => {
                const li = document.createElement('li');
                li.textContent = `${act.type} | ${act.distanceMeters}m | ${act.durationSeconds}s | ${act.calories} cal | Notes: ${act.notes || '-'}`;
                activitiesList.appendChild(li);
            });
        })
        .catch(err => console.error(err));
}

// Handle form submit
activityForm.addEventListener('submit', e => {
    e.preventDefault();

    const payload = {
        type: document.getElementById('type').value,
        startTime: document.getElementById('startTime').value,
        durationSeconds: parseInt(document.getElementById('durationSeconds').value),
        distanceMeters: parseInt(document.getElementById('distanceMeters').value),
        calories: parseInt(document.getElementById('calories').value),
        notes: document.getElementById('notes').value
    };

    fetch(baseUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(res => res.json())
    .then(data => {
        console.log('Created:', data);
        fetchActivities(); // refresh list
        activityForm.reset();
    })
    .catch(err => console.error(err));
});

// Load activities on page load
fetchActivities();
