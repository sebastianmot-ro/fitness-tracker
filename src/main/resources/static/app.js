const userId = 1; // ID-ul user-ului pe care vrem sa-l folosim
const baseUrl = `/users/${userId}/activities`;

const activityForm = document.getElementById('activityForm');
const activitiesList = document.getElementById('activitiesList');
const activitiesContainer = document.getElementById('activitiesContainer');
const loadingEl = document.getElementById('loading');
const emptyEl = document.getElementById('emptyState');
const filterType = document.getElementById('filterType');
const toast = document.getElementById('toast');

let activitiesCache = [];

function showLoading(show = true) {
    loadingEl.style.display = show ? 'block' : 'none';
}
function showEmpty(show = true) {
    emptyEl.style.display = show ? 'block' : 'none';
}
function showToast(message, ms = 2500) {
    toast.textContent = message;
    toast.style.display = 'block';
    setTimeout(() => { toast.style.display = 'none'; }, ms);
}

function formatDuration(seconds) {
    if (!seconds && seconds !== 0) return '-';
    const s = Number(seconds);
    const hrs = Math.floor(s / 3600);
    const mins = Math.floor((s % 3600) / 60);
    const secs = s % 60;
    if (hrs) return `${hrs}h ${mins}m`;
    if (mins) return `${mins}m ${secs}s`;
    return `${secs}s`;
}
function formatDistance(meters) {
    if (meters == null) return '-';
    if (meters >= 1000) return `${(meters/1000).toFixed(2)} km`;
    return `${meters} m`;
}

function typeLabel(type) {
    switch(type) {
        case 'RUN': return 'Run';
        case 'BIKE': return 'Bike';
        case 'SWIM': return 'Swim';
        default: return type || '-';
    }
}

function renderActivities(list) {
    activitiesList.innerHTML = '';

    if (!list || list.length === 0) {
        showEmpty(true);
        return;
    }

    showEmpty(false);

    list.forEach(act => {
        const li = document.createElement('li');
        li.className = 'activity-card';

        const left = document.createElement('div'); left.className = 'activity-left';
        const typeEl = document.createElement('div');
        typeEl.className = 'act-type ' + (act.type === 'RUN' ? 'act-run' : act.type === 'BIKE' ? 'act-bike' : 'act-swim');
        typeEl.textContent = (act.type || '').slice(0,2);

        const info = document.createElement('div'); info.className = 'act-info';
        const title = document.createElement('div'); title.className = 'act-title';
        title.textContent = `${typeLabel(act.type)} • ${new Date(act.startTime).toLocaleString()}`;
        const meta = document.createElement('div'); meta.className = 'act-meta';
        meta.textContent = `${formatDistance(act.distanceMeters)} • ${formatDuration(act.durationSeconds)} • ${act.calories || 0} cal`;

        info.appendChild(title);
        info.appendChild(meta);
        left.appendChild(typeEl);
        left.appendChild(info);

        const right = document.createElement('div'); right.className = 'activity-right';
        right.innerHTML = `<div>${act.notes ? act.notes : '<small class="muted">No notes</small>'}</div>`;

        li.appendChild(left);
        li.appendChild(right);

        activitiesList.appendChild(li);
    });
}

// Fetch existing activities
function fetchActivities() {
    showLoading(true);
    fetch(baseUrl)
        .then(res => {
            if (!res.ok) throw new Error('Failed to fetch activities');
            return res.json();
        })
        .then(data => {
            activitiesCache = Array.isArray(data) ? data : [];
            applyFilter();
        })
        .catch(err => {
            console.error(err);
            showToast('Could not load activities.');
            activitiesCache = [];
            renderActivities([]);
        })
        .finally(() => showLoading(false));
}

function applyFilter() {
    const f = filterType.value;
    const filtered = activitiesCache.filter(a => f === 'ALL' ? true : (a.type === f));
    renderActivities(filtered);
}

// Handle form submit
activityForm.addEventListener('submit', e => {
    e.preventDefault();

    // basic validation
    const type = document.getElementById('type').value;
    const startTime = document.getElementById('startTime').value;
    const durationSeconds = parseInt(document.getElementById('durationSeconds').value);
    const distanceMeters = parseInt(document.getElementById('distanceMeters').value);
    const calories = parseInt(document.getElementById('calories').value);
    const notes = document.getElementById('notes').value;

    if (!type || !startTime || Number.isNaN(durationSeconds) || Number.isNaN(distanceMeters) || Number.isNaN(calories)) {
        showToast('Please complete all required fields.');
        return;
    }

    const payload = {
        type,
        startTime,
        durationSeconds: durationSeconds || 0,
        distanceMeters: distanceMeters || 0,
        calories: calories || 0,
        notes
    };

    // optimistic UI: disable form while sending
    const submitBtn = activityForm.querySelector('button[type="submit"]');
    submitBtn.disabled = true; submitBtn.textContent = 'Adding...';

    fetch(baseUrl, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload)
    })
    .then(res => {
        if (!res.ok) throw new Error('Failed to create activity');
        return res.json();
    })
    .then(data => {
        showToast('Activity added');
        activityForm.reset();
        // refresh
        fetchActivities();
    })
    .catch(err => {
        console.error(err);
        showToast('Could not add activity');
    })
    .finally(() => { submitBtn.disabled = false; submitBtn.textContent = 'Add Activity'; });
});

filterType.addEventListener('change', () => applyFilter());

// Load activities on page load
fetchActivities();

// Accessibility: expose keyboard shortcut to focus form (f)
document.addEventListener('keydown', (e) => {
    if (e.key === 'f' || e.key === 'F') {
        const first = document.getElementById('type');
        if (first) first.focus();
    }
});
