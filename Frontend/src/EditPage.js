import React, { useState, useEffect } from "react";
import axios from "axios";
import "./loginStyles.css";

const EditPage = ({ dogName, dogId }) => {
    const [isLoading, setLoading] = useState(true);
    const [breed, setBreed] = useState();
    const [age, setAge] = useState();
    const [name, setName] = useState(dogName);
    const [weight, setWeight] = useState();
    const [photo, setPhoto] = useState();
    const [previewPhoto, setPreviewPhoto] = useState(null);
    const [success, setSuccess] = useState(false);
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [image, setImage] = useState();

    var breeds = [
        { value: '', text: '--Choose an option--' },
        { value: 'Husky', text: 'Husky' },
        { value: 'Shih Tzu', text: 'Shih Tzu' },
        { value: 'Labrador', text: 'Labrador' },
        { value: 'Pomeranian', text: 'Pomeranian' },
    ]

    const params = new URLSearchParams({
        dogId: dogId
    }).toString();
    const url = "http://localhost:8080/pawsitivelywell/dog/getDog?" + params;
    useEffect(() => {
        axios.get(url).then((response) => {
            if (response.data) {
                setBreed(response.data.breed);
                setAge(response.data.age);
                setWeight(response.data.weight);
                //setPhoto(response.data.photo);
                setPreviewPhoto(null);
                setImage(null);
                setUploadSuccess(false);
                setName(response.data.dogName);
                const data = response.data.photo;
                if (data != null)
                    setPhoto(`data:image/jpeg;base64,${data}`);
                else
                    setPhoto(null);
                setLoading(false);
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }, [dogId]);

    function onChange() {
        setLoading(false);
        setSuccess(false);
        setPreviewPhoto(null);
    }

    function onSubmit(event) {
        var data = new FormData();
        data.append('dogId', dogId)
        data.append('dogName', event.target.form[1].value)
        data.append('breed', event.target.form[2].value)
        data.append('age', event.target.form[3].value)
        data.append('weight', event.target.form[4].value)
        const url = "http://localhost:8080/pawsitivelywell/dog/updateDog"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setSuccess(true);
                console.log("Dog updated successfully")
            } else {
                setSuccess(false);
                console.log("Dog update failed")
            }
        })
            .catch(function (error) {
                console.log(error);
            });
    }

    function onImageChange(e) {
        setPreviewPhoto(e.target.files[0]);
        setImage(URL.createObjectURL(e.target.files[0]))
    }

    function onImageSave(e) {
        console.log(e);
        console.log(dogId);
        const data = new FormData();
        data.append('dogId', dogId)
        data.append(
            "photo",
            previewPhoto
          );
          const url = "http://localhost:8080/pawsitivelywell/dog/uploadPhoto"

          axios.post(url, data, {
  
          }).then((response) => {
              if (response.data) {
                setUploadSuccess(true);
                  console.log("Dog photo updated successfully")
              } else {
                setUploadSuccess(false);
                  console.log("Dog photo update failed")
              }
          })
              .catch(function (error) {
                  console.log(error);
              });
    }

    if (!isLoading) {
        return (
            <div style={{ marginLeft: "2vw" }}>
                <div className="modalHeader">
                    <h2>Edit {name} details</h2>
                </div>
                <div>
                    <form className="form-horizontal form-loanable">
                        <fieldset>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Dog Name</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="text"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="Pupper"
                                        defaultValue={name}
                                        key={name}
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-password" className="col-sm-5">Breed</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <div className="login-password-wrapper">
                                        <select defaultValue={breed} key={breed} onChange={onChange}>{breeds.map((option, index) => (<option className="form-control" key={index} value={option.value}>{option.text}</option>))}</select>
                                    </div>
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Age</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        defaultValue={age}
                                        key={age}
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                            <div className="form-group has-feedback required">
                                <label htmlFor="login-email" className="col-sm-5">Weight (in kg)</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="number"
                                        step="any"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        placeholder="0"
                                        defaultValue={weight}
                                        key={weight}
                                        onChange={onChange}
                                        required
                                    />
                                </div>
                            </div>
                        </fieldset>
                        <div className="form-action">
                            <input
                                type="submit"
                                className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); onSubmit(e); }} value="Update Dog Profile" />
                        </div>
                        {success && <div>
                            <span className="success-alert">Dog Profile updated successfully!</span>
                        </div>}
                    </form>
                </div>
                <div style={{ margin: '2vw' }}>
                    <label htmlFor="login-email" className="col-sm-5">Dog Photo</label><br />
                    { {previewPhoto} && <img src={image || photo} style={{ height: '10vw', width: '10vw' }} alt="No Picture" /> ||
                        {photo} && <img src={photo} style={{ height: '10vw', width: '10vw' }} alt="No Picture" />
                    }
                    
                    <br /><br />
                    <input type="file" multiple accept="image/*" onChange={onImageChange} />
                    <br/>
                    <p>Use jpg/jpeg format</p>
                    <div className="form-action">
                            <input
                                type="submit"
                                className="btn btn-lg btn-primary btn-left" disabled={previewPhoto==null} style={{marginLeft:"0vw"}} onClick={e => { e.preventDefault(); onImageSave(e); }} value="Upload" />
                        </div>
                        {uploadSuccess && <div>
                            <span className="success-alert">Dog photo uploaded successfully!</span>
                        </div>}
                </div>
            </div>
        );
    } else {
        return (
            <div>Loading...</div>
        )
    }
}

export default EditPage;