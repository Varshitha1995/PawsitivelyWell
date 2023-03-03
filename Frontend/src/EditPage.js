import React, { useState, useEffect } from "react";
import axios from "axios";
import "./loginStyles.css";

const EditPage = ({ dogName, dogId, emailId }) => {
    const [isLoading, setLoading] = useState(true);
    const [breed, setBreed] = useState();
    const [age, setAge] = useState();
    const [name, setName] = useState(dogName);
    const [mydogId, setDogId] = useState(dogId);
    const [weight, setWeight] = useState();
    const [photo, setPhoto] = useState();
    const [previewPhoto, setPreviewPhoto] = useState(null);
    const [success, setSuccess] = useState(false);
    const [removeSuccess, setRemoveSuccess] = useState(false);
    const [uploadSuccess, setUploadSuccess] = useState(false);
    const [image, setImage] = useState();

    var breeds = [
        { value: 'Affenpinscher', text: 'Affenpinscher' },
        { value: 'Afghan Hound', text: 'Afghan Hound' },
        { value: 'Airedale Terrier', text: 'Airedale Terrier' },
        { value: 'Akita', text: 'Akita' },
        { value: 'Alaskan Malamute', text: 'Alaskan Malamute' },
        { value: 'American Bulldog', text: 'American Bulldog' },
        { value: 'American English Coonhound', text: 'American English Coonhound' },
        { value: 'American Eskimo Dog', text: 'American Eskimo Dog' },
        { value: 'American Foxhound', text: 'American Foxhound' },
        { value: 'American Hairless Terrier', text: 'American Hairless Terrier' },
        { value: 'American Leopard Hound', text: 'American Leopard Hound' },
        { value: 'American Staffordshire Terrier', text: 'American Staffordshire Terrier' },
        { value: 'American Water Spaniel', text: 'American Water Spaniel' },
        { value: 'Anatolian Shepherd Dog', text: 'Anatolian Shepherd Dog' },
        { value: 'Appenzeller Sennenhund', text: 'Appenzeller Sennenhund' },
        { value: 'Australian Cattle Dog', text: 'Australian Cattle Dog' },
        { value: 'Australian Kelpie', text: 'Australian Kelpie' },
        { value: 'Australian Shepherd', text: 'Australian Shepherd' },
        { value: 'Australian Stumpy Tail Cattle Dog', text: 'Australian Stumpy Tail Cattle Dog' },
        { value: 'Australian Terrier', text: 'Australian Terrier' },
        { value: 'Azawakh', text: 'Azawakh' },
        { value: 'Barbet', text: 'Barbet' },
        { value: 'Basenji', text: 'Basenji' },
        { value: 'Basset Fauve de Bretagne', text: 'Basset Fauve de Bretagne' },
        { value: 'Basset Hound', text: 'Basset Hound' },
        { value: 'Bavarian Mountain Scent Hound', text: 'Bavarian Mountain Scent Hound' },
        { value: 'Beagle', text: 'Beagle' },
        { value: 'Bearded Collie', text: 'Bearded Collie' },
        { value: 'Beauceron', text: 'Beauceron' },
        { value: 'Bedlington Terrier', text: 'Bedlington Terrier' },
        { value: 'Belgian Laekenois', text: 'Belgian Laekenois' },
        { value: 'Belgian Malinois', text: 'Belgian Malinois' },
        { value: 'Belgian Sheepdog', text: 'Belgian Sheepdog' },
        { value: 'Belgian Tervuren', text: 'Belgian Tervuren' },
        { value: 'Bergamasco Sheepdog', text: 'Bergamasco Sheepdog' },
        { value: 'Berger Picard', text: 'Berger Picard' },
        { value: 'Bernese Mountain Dog', text: 'Bernese Mountain Dog' },
        { value: 'Bichon Frise', text: 'Bichon Frise' },
        { value: 'Biewer Terrier', text: 'Biewer Terrier' },
        { value: 'Black and Tan Coonhound', text: 'Black and Tan Coonhound' },
        { value: 'Black Russian Terrier', text: 'Black Russian Terrier' },
        { value: 'Bloodhound', text: 'Bloodhound' },
        { value: 'Bluetick Coonhound', text: 'Bluetick Coonhound' },
        { value: 'Boerboel', text: 'Boerboel' },
        { value: 'Bohemian Shepherd', text: 'Bohemian Shepherd' },
        { value: 'Bolognese', text: 'Bolognese' },
        { value: 'Border Collie', text: 'Border Collie' },
        { value: 'Border Terrier', text: 'Border Terrier' },
        { value: 'Borzoi', text: 'Borzoi' },
        { value: 'Boston Terrier', text: 'Boston Terrier' },
        { value: 'Bouvier des Flandres', text: 'Bouvier des Flandres' },
        { value: 'Boxer', text: 'Boxer' },
        { value: 'Boykin Spaniel', text: 'Boykin Spaniel' },
        { value: 'Bracco Italiano', text: 'Bracco Italiano' },
        { value: 'Braque du Bourbonnais', text: 'Braque du Bourbonnais' },
        { value: 'Braque Francais Pyrenean', text: 'Braque Francais Pyrenean' },
        { value: 'Briard', text: 'Briard' },
        { value: 'Brittany', text: 'Brittany' },
        { value: 'Broholmer', text: 'Broholmer' },
        { value: 'Brussels Griffon', text: 'Brussels Griffon' },
        { value: 'Bull Terrier', text: 'Bull Terrier' },
        { value: 'Bulldog', text: 'Bulldog' },
        { value: 'Bullmastiff', text: 'Bullmastiff' },
        { value: 'Cairn Terrier', text: 'Cairn Terrier' },
        { value: 'Canaan Dog', text: 'Canaan Dog' },
        { value: 'Cane Corso', text: 'Cane Corso' },
        { value: 'Cardigan Welsh Corgi', text: 'Cardigan Welsh Corgi' },
        { value: 'Carolina Dog', text: 'Carolina Dog' },
        { value: 'Catahoula Leopard Dog', text: 'Catahoula Leopard Dog' },
        { value: 'Caucasian Shepherd Dog', text: 'Caucasian Shepherd Dog' },
        { value: 'Cavalier King Charles Spaniel', text: 'Cavalier King Charles Spaniel' },
        { value: 'Central Asian Shepherd Dog', text: 'Central Asian Shepherd Dog' },
        { value: 'Cesky Terrier', text: 'Cesky Terrier' },
        { value: 'Chesapeake Bay Retriever', text: 'Chesapeake Bay Retriever' },
        { value: 'Chihuahua', text: 'Chihuahua' },
        { value: 'Chinese Crested', text: 'Chinese Crested' },
        { value: 'Chinese Shar-Pei', text: 'Chinese Shar-Pei' },
        { value: 'Chinook', text: 'Chinook' },
        { value: 'Chow Chow', text: 'Chow Chow' },
        { value: 'Cirneco dellâ€™Etna', text: 'Cirneco dellâ€™Etna' },
        { value: 'Clumber Spaniel', text: 'Clumber Spaniel' },
        { value: 'Cocker Spaniel', text: 'Cocker Spaniel' },
        { value: 'Collie', text: 'Collie' },
        { value: 'Coton de Tulear', text: 'Coton de Tulear' },
        { value: 'Croatian Sheepdog', text: 'Croatian Sheepdog' },
        { value: 'Curly-Coated Retriever', text: 'Curly-Coated Retriever' },
        { value: 'Czechoslovakian Vlcak', text: 'Czechoslovakian Vlcak' },
        { value: 'Dachshund', text: 'Dachshund' },
        { value: 'Dalmatian', text: 'Dalmatian' },
        { value: 'Dandie Dinmont Terrier', text: 'Dandie Dinmont Terrier' },
        { value: 'Danish-Swedish Farmdog', text: 'Danish-Swedish Farmdog' },
        { value: 'Deutscher Wachtelhund', text: 'Deutscher Wachtelhund' },
        { value: 'Doberman Pinscher', text: 'Doberman Pinscher' },
        { value: 'Dogo Argentino', text: 'Dogo Argentino' },
        { value: 'Dogue de Bordeaux', text: 'Dogue de Bordeaux' },
        { value: 'Drentsche Patrijshond', text: 'Drentsche Patrijshond' },
        { value: 'Drever', text: 'Drever' },
        { value: 'Dutch Shepherd', text: 'Dutch Shepherd' },
        { value: 'English Cocker Spaniel', text: 'English Cocker Spaniel' },
        { value: 'English Foxhound', text: 'English Foxhound' },
        { value: 'English Setter', text: 'English Setter' },
        { value: 'English Springer Spaniel', text: 'English Springer Spaniel' },
        { value: 'English Toy Spaniel', text: 'English Toy Spaniel' },
        { value: 'Entlebucher Mountain Dog', text: 'Entlebucher Mountain Dog' },
        { value: 'Estrela Mountain Dog', text: 'Estrela Mountain Dog' },
        { value: 'Eurasier', text: 'Eurasier' },
        { value: 'Field Spaniel', text: 'Field Spaniel' },
        { value: 'Finnish Lapphund', text: 'Finnish Lapphund' },
        { value: 'Finnish Spitz', text: 'Finnish Spitz' },
        { value: 'Flat-Coated Retriever', text: 'Flat-Coated Retriever' },
        { value: 'French Bulldog', text: 'French Bulldog' },
        { value: 'French Spaniel', text: 'French Spaniel' },
        { value: 'German Longhaired Pointer', text: 'German Longhaired Pointer' },
        { value: 'German Pinscher', text: 'German Pinscher' },
        { value: 'German Shepherd Dog', text: 'German Shepherd Dog' },
        { value: 'German Shorthaired Pointer', text: 'German Shorthaired Pointer' },
        { value: 'German Spitz', text: 'German Spitz' },
        { value: 'German Wirehaired Pointer', text: 'German Wirehaired Pointer' },
        { value: 'Giant Schnauzer', text: 'Giant Schnauzer' },
        { value: 'Glen of Imaal Terrier', text: 'Glen of Imaal Terrier' },
        { value: 'Golden Retriever', text: 'Golden Retriever' },
        { value: 'Gordon Setter', text: 'Gordon Setter' },
        { value: 'Grand Basset Griffon VendÃ©en', text: 'Grand Basset Griffon VendÃ©en' },
        { value: 'Great Dane', text: 'Great Dane' },
        { value: 'Great Pyrenees', text: 'Great Pyrenees' },
        { value: 'Greater Swiss Mountain Dog', text: 'Greater Swiss Mountain Dog' },
        { value: 'Greyhound', text: 'Greyhound' },
        { value: 'Hamiltonstovare', text: 'Hamiltonstovare' },
        { value: 'Hanoverian Scenthound', text: 'Hanoverian Scenthound' },
        { value: 'Harrier', text: 'Harrier' },
        { value: 'Havanese', text: 'Havanese' },
        { value: 'Hokkaido', text: 'Hokkaido' },
        { value: 'Hovawart', text: 'Hovawart' },
        { value: 'Ibizan Hound', text: 'Ibizan Hound' },
        { value: 'Icelandic Sheepdog', text: 'Icelandic Sheepdog' },
        { value: 'Irish Red and White Setter', text: 'Irish Red and White Setter' },
        { value: 'Irish Setter', text: 'Irish Setter' },
        { value: 'Irish Terrier', text: 'Irish Terrier' },
        { value: 'Irish Water Spaniel', text: 'Irish Water Spaniel' },
        { value: 'Irish Wolfhound', text: 'Irish Wolfhound' },
        { value: 'Italian Greyhound', text: 'Italian Greyhound' },
        { value: 'Jagdterrier', text: 'Jagdterrier' },
        { value: 'Japanese Chin', text: 'Japanese Chin' },
        { value: 'Japanese Spitz', text: 'Japanese Spitz' },
        { value: 'Jindo', text: 'Jindo' },
        { value: 'Kai Ken', text: 'Kai Ken' },
        { value: 'Karelian Bear Dog', text: 'Karelian Bear Dog' },
        { value: 'Keeshond', text: 'Keeshond' },
        { value: 'Kerry Blue Terrier', text: 'Kerry Blue Terrier' },
        { value: 'Kishu Ken', text: 'Kishu Ken' },
        { value: 'Komondor', text: 'Komondor' },
        { value: 'Kromfohrlander', text: 'Kromfohrlander' },
        { value: 'Kuvasz', text: 'Kuvasz' },
        { value: 'Labrador Retriever', text: 'Labrador Retriever' },
        { value: 'Lagotto Romagnolo', text: 'Lagotto Romagnolo' },
        { value: 'Lakeland Terrier', text: 'Lakeland Terrier' },
        { value: 'Lancashire Heeler', text: 'Lancashire Heeler' },
        { value: 'Lapponian Herder', text: 'Lapponian Herder' },
        { value: 'Leonberger', text: 'Leonberger' },
        { value: 'Lhasa Apso', text: 'Lhasa Apso' },
        { value: 'LÃ¶wchen', text: 'LÃ¶wchen' },
        { value: 'Maltese', text: 'Maltese' },
        { value: 'Manchester Terrier (Standard)', text: 'Manchester Terrier (Standard)' },
        { value: 'Manchester Terrier (Toy)', text: 'Manchester Terrier (Toy)' },
        { value: 'Mastiff', text: 'Mastiff' },
        { value: 'Miniature American Shepherd', text: 'Miniature American Shepherd' },
        { value: 'Miniature Bull Terrier', text: 'Miniature Bull Terrier' },
        { value: 'Miniature Pinscher', text: 'Miniature Pinscher' },
        { value: 'Miniature Schnauzer', text: 'Miniature Schnauzer' },
        { value: 'Mountain Cur', text: 'Mountain Cur' },
        { value: 'Mudi', text: 'Mudi' },
        { value: 'Neapolitan Mastiff', text: 'Neapolitan Mastiff' },
        { value: 'Nederlandse Kooikerhondje', text: 'Nederlandse Kooikerhondje' },
        { value: 'Newfoundland', text: 'Newfoundland' },
        { value: 'Norfolk Terrier', text: 'Norfolk Terrier' },
        { value: 'Norrbottenspets', text: 'Norrbottenspets' },
        { value: 'Norwegian Buhund', text: 'Norwegian Buhund' },
        { value: 'Norwegian Elkhound', text: 'Norwegian Elkhound' },
        { value: 'Norwegian Lundehund', text: 'Norwegian Lundehund' },
        { value: 'Norwich Terrier', text: 'Norwich Terrier' },
        { value: 'Nova Scotia Duck Tolling Retriever', text: 'Nova Scotia Duck Tolling Retriever' },
        { value: 'Old English Sheepdog', text: 'Old English Sheepdog' },
        { value: 'Otterhound', text: 'Otterhound' },
        { value: 'Papillon', text: 'Papillon' },
        { value: 'Parson Russell Terrier', text: 'Parson Russell Terrier' },
        { value: 'Pekingese', text: 'Pekingese' },
        { value: 'Pembroke Welsh Corgi', text: 'Pembroke Welsh Corgi' },
        { value: 'Perro de Presa Canario', text: 'Perro de Presa Canario' },
        { value: 'Peruvian Inca Orchid', text: 'Peruvian Inca Orchid' },
        { value: 'Petit Basset Griffon VendÃ©en', text: 'Petit Basset Griffon VendÃ©en' },
        { value: 'Pharaoh Hound', text: 'Pharaoh Hound' },
        { value: 'Plott Hound', text: 'Plott Hound' },
        { value: 'Pointer', text: 'Pointer' },
        { value: 'Polish Lowland Sheepdog', text: 'Polish Lowland Sheepdog' },
        { value: 'Pomeranian', text: 'Pomeranian' },
        { value: 'Poodle (Miniature)', text: 'Poodle (Miniature)' },
        { value: 'Poodle (Standard)', text: 'Poodle (Standard)' },
        { value: 'Poodle (Toy)', text: 'Poodle (Toy)' },
        { value: 'Porcelaine', text: 'Porcelaine' },
        { value: 'Portuguese Podengo', text: 'Portuguese Podengo' },
        { value: 'Portuguese Podengo Pequeno', text: 'Portuguese Podengo Pequeno' },
        { value: 'Portuguese Pointer', text: 'Portuguese Pointer' },
        { value: 'Portuguese Sheepdog', text: 'Portuguese Sheepdog' },
        { value: 'Portuguese Water Dog', text: 'Portuguese Water Dog' },
        { value: 'Pudelpointer', text: 'Pudelpointer' },
        { value: 'Pug', text: 'Pug' },
        { value: 'Puli', text: 'Puli' },
        { value: 'Pumi', text: 'Pumi' },
        { value: 'Pyrenean Mastiff', text: 'Pyrenean Mastiff' },
        { value: 'Pyrenean Shepherd', text: 'Pyrenean Shepherd' },
        { value: 'Rafeiro do Alentejo', text: 'Rafeiro do Alentejo' },
        { value: 'Rat Terrier', text: 'Rat Terrier' },
        { value: 'Redbone Coonhound', text: 'Redbone Coonhound' },
        { value: 'Rhodesian Ridgeback', text: 'Rhodesian Ridgeback' },
        { value: 'Romanian Mioritic Shepherd Dog', text: 'Romanian Mioritic Shepherd Dog' },
        { value: 'Rottweiler', text: 'Rottweiler' },
        { value: 'Russell Terrier', text: 'Russell Terrier' },
        { value: 'Russian Toy', text: 'Russian Toy' },
        { value: 'Russian Tsvetnaya Bolonka', text: 'Russian Tsvetnaya Bolonka' },
        { value: 'Saint Bernard', text: 'Saint Bernard' },
        { value: 'Saluki', text: 'Saluki' },
        { value: 'Samoyed', text: 'Samoyed' },
        { value: 'Schapendoes', text: 'Schapendoes' },
        { value: 'Schipperke', text: 'Schipperke' },
        { value: 'Scottish Deerhound', text: 'Scottish Deerhound' },
        { value: 'Scottish Terrier', text: 'Scottish Terrier' },
        { value: 'Sealyham Terrier', text: 'Sealyham Terrier' },
        { value: 'Segugio Italiano', text: 'Segugio Italiano' },
        { value: 'Shetland Sheepdog', text: 'Shetland Sheepdog' },
        { value: 'Shiba Inu', text: 'Shiba Inu' },
        { value: 'Shih Tzu', text: 'Shih Tzu' },
        { value: 'Shikoku', text: 'Shikoku' },
        { value: 'Siberian Husky', text: 'Siberian Husky' },
        { value: 'Silky Terrier', text: 'Silky Terrier' },
        { value: 'Skye Terrier', text: 'Skye Terrier' },
        { value: 'Sloughi', text: 'Sloughi' },
        { value: 'Slovakian Wirehaired Pointer', text: 'Slovakian Wirehaired Pointer' },
        { value: 'Slovensky Cuvac', text: 'Slovensky Cuvac' },
        { value: 'Slovensky Kopov', text: 'Slovensky Kopov' },
        { value: 'Small Munsterlander Pointer', text: 'Small Munsterlander Pointer' },
        { value: 'Smooth Fox Terrier', text: 'Smooth Fox Terrier' },
        { value: 'Soft Coated Wheaten Terrier', text: 'Soft Coated Wheaten Terrier' },
        { value: 'Spanish Mastiff', text: 'Spanish Mastiff' },
        { value: 'Spanish Water Dog', text: 'Spanish Water Dog' },
        { value: 'Spinone Italiano', text: 'Spinone Italiano' },
        { value: 'Stabyhoun', text: 'Stabyhoun' },
        { value: 'Staffordshire Bull Terrier', text: 'Staffordshire Bull Terrier' },
        { value: 'Standard Schnauzer', text: 'Standard Schnauzer' },
        { value: 'Sussex Spaniel', text: 'Sussex Spaniel' },
        { value: 'Swedish Lapphund', text: 'Swedish Lapphund' },
        { value: 'Swedish Vallhund', text: 'Swedish Vallhund' },
        { value: 'Taiwan Dog', text: 'Taiwan Dog' },
        { value: 'Teddy Roosevelt Terrier', text: 'Teddy Roosevelt Terrier' },
        { value: 'Thai Ridgeback', text: 'Thai Ridgeback' },
        { value: 'Tibetan Mastiff', text: 'Tibetan Mastiff' },
        { value: 'Tibetan Spaniel', text: 'Tibetan Spaniel' },
        { value: 'Tibetan Terrier', text: 'Tibetan Terrier' },
        { value: 'Tornjak', text: 'Tornjak' },
        { value: 'Tosa', text: 'Tosa' },
        { value: 'Toy Fox Terrier', text: 'Toy Fox Terrier' },
        { value: 'Transylvanian Hound', text: 'Transylvanian Hound' },
        { value: 'Treeing Tennessee Brindle', text: 'Treeing Tennessee Brindle' },
        { value: 'Treeing Walker Coonhound', text: 'Treeing Walker Coonhound' },
        { value: 'Vizsla', text: 'Vizsla' },
        { value: 'Weimaraner', text: 'Weimaraner' },
        { value: 'Welsh Springer Spaniel', text: 'Welsh Springer Spaniel' },
        { value: 'Welsh Terrier', text: 'Welsh Terrier' },
        { value: 'West Highland White Terrier', text: 'West Highland White Terrier' },
        { value: 'Wetterhoun', text: 'Wetterhoun' },
        { value: 'Whippet', text: 'Whippet' },
        { value: 'Wire Fox Terrier', text: 'Wire Fox Terrier' },
        { value: 'Wirehaired Pointing Griffon', text: 'Wirehaired Pointing Griffon' },
        { value: 'Wirehaired Vizsla', text: 'Wirehaired Vizsla' },
        { value: 'Working Kelpie', text: 'Working Kelpie' },
        { value: 'Xoloitzcuintli', text: 'Xoloitzcuintli' },
        { value: 'Yakutian Laika', text: 'Yakutian Laika' },
        { value: 'Yorkshire Terrier', text: 'Yorkshire Terrier' },
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
                setDogId(response.data.dog_id);
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
        setRemoveSuccess(false);
    }

    function onSubmit(event) {
        var data = new FormData();
        data.append('dogId', dogId)
        data.append('dogName', event.target.form[2].value)
        data.append('breed', event.target.form[3].value)
        data.append('age', event.target.form[4].value)
        data.append('weight', event.target.form[5].value)
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

    function onRemove(event) {
        var data = new FormData();
        data.append('dogId', dogId)
        data.append('emailId', emailId)
        const url = "http://localhost:8080/pawsitivelywell/user/removeDog"

        axios.post(url, data, {

        }).then((response) => {
            if (response.data) {
                setRemoveSuccess(true);
                console.log("Dog removed successfully")
            } else {
                setRemoveSuccess(false);
                console.log("Dog remove failed")
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
                                <label htmlFor="login-email" className="col-sm-5">Dog ID</label>
                                <div className="col-sm-7">
                                    <span className="form-control-feedback" aria-hidden="true"></span>
                                    <input
                                        type="text"
                                        name="email"
                                        id="login-email"
                                        className="form-control"
                                        style={{ cursor: "not-allowed" }}
                                        placeholder="Pupper"
                                        defaultValue={mydogId}
                                        key={mydogId}
                                        readOnly
                                        required
                                    />
                                </div>
                            </div>
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
                                <label htmlFor="login-email" className="col-sm-5">Weight (in lbs)</label>
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
                        <div className="form-action">
                            <input
                                type="submit" style={{ backgroundColor: "red" }}
                                className="btn btn-lg btn-primary btn-left" onClick={e => { e.preventDefault(); onRemove(e); }} value="Remove Dog Profile" />
                        </div>
                        {success && <div>
                            <span className="success-alert">Dog Profile updated successfully!</span>
                        </div>}
                        {removeSuccess && <div>
                            <span className="success-alert">Dog Profile removed successfully!</span>
                        </div>}
                    </form>
                </div>
                <div style={{ margin: '2vw' }}>
                    <label htmlFor="login-email" className="col-sm-5">Dog Photo</label><br />
                    {{ previewPhoto } && <img src={image || photo} style={{ height: '10vw', width: '10vw' }} alt="No Picture" /> ||
                        { photo } && <img src={photo} style={{ height: '10vw', width: '10vw' }} alt="No Picture" />
                    }

                    <br /><br />
                    <input type="file" multiple accept="image/*" onChange={onImageChange} />
                    <br />
                    <p>Use jpg/jpeg format</p>
                    <div className="form-action">
                        <input
                            type="submit"
                            className="btn btn-lg btn-primary btn-left" disabled={previewPhoto == null} style={{ marginLeft: "0vw" }} onClick={e => { e.preventDefault(); onImageSave(e); }} value="Upload" />
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