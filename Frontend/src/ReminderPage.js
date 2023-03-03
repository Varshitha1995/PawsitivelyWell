import React from "react";

const ReminderPage = ({dogName, dogId, emailId}) => {
    return (
        <div>
            <h3>Reminder page</h3>
            <h5>{dogName}</h5>
            <p>{dogId}</p>
            <p>{emailId}</p>
        </div>
    );
}

export default ReminderPage;